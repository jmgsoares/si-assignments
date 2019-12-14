package pt.onept.mei.is1920.assignment.kafka.common.type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Order {
    public static final int PRICE_MAX = 1000;
    public static final int QUANTITY_MAX = 100;
    private Item item;
    private float price;
    private int quantity;
    private Date timeStamp;
}
