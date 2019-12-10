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
    private Item item;
    private float price;
    private int quantity;
    private Country countryOfOrigin;
}
