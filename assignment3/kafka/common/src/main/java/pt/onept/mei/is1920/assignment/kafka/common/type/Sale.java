package pt.onept.mei.is1920.assignment.kafka.common.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Sale {
    public static final int PRICE_MAX = 10000;
    public static final int QUANTITY_MAX = 10;
    private Item item;
    private float price;
    private int quantity;
    private Country country;
}