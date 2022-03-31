package ee.alekal.storage.engine;


import com.fasterxml.jackson.databind.ObjectMapper;
import ee.alekal.storage.model.dto.ItemDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static ee.alekal.storage.utils.AppConstants.BASE_URL;
import static ee.alekal.storage.utils.AppConstants.ITEM_API_PATH;
import static ee.alekal.storage.utils.AppConstants.PICTURE;
import static ee.alekal.storage.utils.AppConstants.SEPARATOR;

/**
 * Fills UI with some test data
 */
public class TestEngine {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String TOP_ITEM_PATH =
            BASE_URL.concat(ITEM_API_PATH).concat(SEPARATOR).concat("admin");
    private final int ITEM_COUNT = 10;

    public void runEngine() {
        addTopItems();
        addSubItemsLevel1();
    }

    private void addTopItems() {
        for (var item: getTopItems()) {
            RequestBody requestBody;
            try {
                requestBody = RequestBody
                        .create(JSON,objectMapper.writeValueAsString(item));
                var request = new Request.Builder()
                        .url(TOP_ITEM_PATH)
                        .post(requestBody)
                        .build();
                client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addSubItemsLevel1() {
        for (int i = 5; i < ITEM_COUNT; i++) {
            RequestBody requestBody;
            try {
                requestBody = RequestBody
                        .create(JSON,objectMapper.writeValueAsString(createSubItem(i)));
                var subItemPath =
                        BASE_URL.concat(ITEM_API_PATH)
                                .concat(SEPARATOR)
                                .concat(Integer.toString(i))
                                .concat(SEPARATOR)
                                .concat("admin");
                var request = new Request.Builder()
                        .url(subItemPath)
                        .post(requestBody)
                        .build();
                client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<ItemDto> getTopItems() {
        var result = new ArrayList<ItemDto>();
        for (int i = 5; i < ITEM_COUNT; i++) {
            final var itemDto = ItemDto.builder()
                    .color("RED")
                    .name("Box-" + i)
                    .picturePath(PICTURE)
                    .serialNumber("a-" + i + "-b" + i + 1 + "-c" + i * 4 + "-d")
                    .size(BigInteger.valueOf(100L))
                    .build();
            result.add(itemDto);
        }
        return result;
    }

    private ItemDto createSubItem(int i) {
        return ItemDto.builder()
                .color("COLOR")
                .name("Sub-Box-" + i)
                .picturePath(PICTURE)
                .serialNumber("a-" + i + "-b" + i + 2 + "-c" + i * 3 + "-d")
                .size(BigInteger.valueOf(50L))
                .build();
    }

    public static void main(String[] args) {
        var testEngine = new TestEngine();
        testEngine.runEngine();
    }
}
