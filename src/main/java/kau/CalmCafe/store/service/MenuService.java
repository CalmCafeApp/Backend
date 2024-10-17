package kau.CalmCafe.store.service;

import jakarta.transaction.Transactional;
import kau.CalmCafe.store.domain.Menu;
import kau.CalmCafe.store.domain.Store;
import kau.CalmCafe.store.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public List<Menu> findMenuListByStore(Store store) {
        return menuRepository.findAllByStore(store);
    }

}
