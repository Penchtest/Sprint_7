package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.dto.OrderRequest;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.generator.OrderRequestGenerator.getRandomOrderRequest;


@RunWith(Parameterized.class)
public class OrderParametrizedTest {
    private OrderClient orderClient;
    private final String[] color;
    private Integer track;


    public OrderParametrizedTest(String[] color) {
        this.color = color;
           }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }


    @After
    public void tearDown() {
       if (track != null)
           orderClient.cancelOrder(track)
                    .assertThat()
                   .statusCode(SC_CREATED)
                   .body("ok", equalTo(true));
    }


    @Parameterized.Parameters
    public static Object [][] getColor(){
        return new Object[][]{
                {new String[]{""}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"GREY", "BLACK"}}
        };
    }


    @Test
    @DisplayName("Order should be created")
    public void OrderShouldBeCreated() {

        //create

        OrderRequest randomOrderRequest = getRandomOrderRequest();
        randomOrderRequest.setColor(color);
      Integer track =  orderClient.createOrder(randomOrderRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue())
               .extract()
              .path("track");
    }
}
