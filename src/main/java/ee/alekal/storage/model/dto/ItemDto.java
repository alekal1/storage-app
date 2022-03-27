package ee.alekal.storage.model.dto;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class ItemDto {

    private String name;
    private String picturePath;
    private String serialNumber;
    private String color;
    private BigInteger size;

    public static ItemDtoBuilder builder() {
        return new ItemDtoBuilder();
    }

    public static class ItemDtoBuilder {
        private String name;
        private String picturePath;
        private String serialNumber;
        private String color;
        private BigInteger size;

        public ItemDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemDtoBuilder picturePath(String picturePath) {
            this.picturePath = picturePath;
            return this;
        }

        public ItemDtoBuilder serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public ItemDtoBuilder color(String color) {
            this.color = color;
            return this;
        }

        public ItemDtoBuilder size(BigInteger size) {
            this.size = size;
            return this;
        }

        public ItemDto build() {
            var dto = new ItemDto();
            dto.name = name;
            dto.color = color;
            dto.size = size;
            dto.picturePath = picturePath;
            dto.serialNumber = serialNumber;
            return dto;
        }
    }

}
