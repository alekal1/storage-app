export class ItemDto {
  name: string;
  picturePath: string;
  serialNumber: string;
  color: string;
  lastAccessedOn: Date;
  size: number

  constructor(
    name: string,
    picturePath: string,
    serialNumber: string,
    color: string,
    size: number,
    lastAccessedOn?: Date
  ) {
    this.name = name;
    this.picturePath = picturePath;
    this.serialNumber = serialNumber;
    this.color = color;
    this.size = size;
    this.lastAccessedOn = lastAccessedOn;
  }
}
