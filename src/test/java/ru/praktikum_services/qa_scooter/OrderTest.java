package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.client.OrderClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }


    @Test
    @DisplayName("Order list should be returned")
    public void orderListShouldBeReturned() {
        orderClient.getOrderList()
            .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());
    }
}
