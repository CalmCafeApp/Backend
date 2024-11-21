package kau.CalmCafe.global.s3;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class CsvParser {
    private static final String FIRST_COLUMN = "weekday";
    private static final String SECOND_COLUMN = "hour";
    private static final String THIRD_COLUMN = "predicted_people";

    // csv 파일을 읽어 Map으로 변환 후 반환
    public Map<String, Map<Integer, Integer>> parse(InputStream inputStream) throws IOException {
        Map<String, Map<Integer, Integer>> congestionData = new HashMap<>();

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN)
                .withSkipHeaderRecord(true) // 헤더 행은 스킵
                .parse(new InputStreamReader(inputStream));

        for (CSVRecord record : records) {
            String weekday = record.get(FIRST_COLUMN);
            Integer hour = Integer.parseInt(record.get(SECOND_COLUMN));
            Integer predictedPeople = Integer.parseInt(record.get(THIRD_COLUMN));

            // weekday 키가 congestionData에 없으면,
            congestionData.computeIfAbsent(weekday, k -> new HashMap<>())
                    .put(hour, predictedPeople);
        }
        return congestionData;
    }

    /*
    {
        "Monday": {
            9: 12,
            10: 8,
            11: 15
        },
        "Tuesday": {
            9: 9,
            10: 11,
            11: 13
        }
    }
    */

}

