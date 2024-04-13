export class MoneyUtils {

  public static round(money: number): number {
    const rounded : number = Math.round(money);
    const integer = Math.floor(rounded / 10);
    const decimal = rounded % 10;
    let roundedDecimal = 0;
    if (decimal > 0 && decimal <= 5) {
      roundedDecimal = 0.5;
    } else if (decimal > 5) {
      roundedDecimal = 1;
    }

    const result = (integer + roundedDecimal) * 10;
    return result;
  }
}
