package kau.CalmCafe.menu.service;

import static kau.CalmCafe.menu.converter.MenuModifyConverter.*;
import static org.apache.logging.log4j.util.Strings.isEmpty;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;
import kau.CalmCafe.global.s3.AmazonS3Manager;
import kau.CalmCafe.menu.converter.MenuModifyConverter;
import kau.CalmCafe.menu.dto.DiscountedMenuDto;
import kau.CalmCafe.menu.dto.MenuModifyResponseDto;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.MenuRepository;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuModifyService {
    private final AmazonS3Manager amazonS3Manager;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuModifyResponseDto deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.MENU_NOT_FOUND));
        menuRepository.delete(menu);
        return toDeleteResponseDto(menu);
    }

    @Transactional
    public MenuModifyResponseDto updateMenu(Long menuId, MultipartFile menuImage, Integer price) throws IOException {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.MENU_NOT_FOUND));

        updateMenuImage(menuImage, menu);

        menu.setPrice(price);

        menuRepository.save(menu);
        return toUpdateResponseDto(menu);
    }

    @Transactional
    public void updateMenuImage(MultipartFile file, Menu menu) throws IOException {
        if (file == null) return;

        String uploadFileUrl;
        String dirName = "menu/";
        String contentType = file.getContentType();

        if (ObjectUtils.isEmpty(contentType)) {
            throw GeneralException.of(ErrorCode.MENU_IMAGE_UPLOAD_FAILED);
        }

        MediaType mediaType = amazonS3Manager.contentType(Objects.requireNonNull(file.getOriginalFilename()));
        if (mediaType == null || !(mediaType.equals(MediaType.IMAGE_PNG) || mediaType.equals(
                MediaType.IMAGE_JPEG))) {
            throw GeneralException.of(ErrorCode.MENU_IMAGE_UPLOAD_FAILED);
        }

        // 이전 메뉴 이미지가 존재하는지 확인
        if (!isEmpty(menu.getImage())) {
            // 기존 메뉴 이미지를 S3에서 삭제
            String previousFilePath = menu.getImage();
            amazonS3Manager.delete(previousFilePath);
        }

        java.io.File uploadFile = amazonS3Manager.convert(file)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        String fileName = dirName + AmazonS3Manager.generateFileName(file);
        uploadFileUrl = amazonS3Manager.putS3(uploadFile, fileName);

        menu.setImage(uploadFileUrl);
    }

    @Transactional
    public MenuModifyResponseDto registerMenu(Long storeId, String name, Integer price, MultipartFile menuImage) throws IOException {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.STORE_NOT_FOUND));

        Menu newMenu = Menu.builder()
                .store(store)
                .name(name)
                .price(price)
                .pointPrice(0)
                .pointDiscount(0)
                .build();

        updateMenuImage(menuImage, newMenu);

        Menu savedMenu = menuRepository.save(newMenu);
        return toResponseDto(savedMenu);
    }

    @Transactional(readOnly = true)
    public List<DiscountedMenuDto> getDiscountedMenus(Long storeId) {
        List<Menu> discountedMenus = menuRepository.findDiscountedMenusByStoreId(storeId);
        return toDiscountedMenuDtoList(discountedMenus);
    }

    @Transactional(readOnly = true)
    public List<MenuModifyResponseDto> getNonDiscountedMenus(Long storeId) {
        List<Menu> nonDiscountedMenus = menuRepository.findNonDiscountedMenusByStoreId(storeId);
        return nonDiscountedMenus.stream()
                .map(MenuModifyConverter::toResponseDto)
                .collect(Collectors.toList());
    }
}