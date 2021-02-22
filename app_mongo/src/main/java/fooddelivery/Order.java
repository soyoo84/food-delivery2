package fooddelivery;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//https://tinnia.github.io/Portfolio/pjt/everywherescrap/2/
//@Entity
//@Table(name="주문_table")
@Document
public class Order {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private String id;// = UUID.randomUUID();
    private String item;
    private Integer 수량;
    private String 상태;
    private String 가게;
    private Long 가격;

    //@PostPersist
    public void onPostPersist(){

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.결제이력 결제이력 = new fooddelivery.external.결제이력();

        // this is Context Mapping (Anti-corruption Layer)
        결제이력.setOrderId(String.valueOf(getId()));
        if(get가격()!=null)
            결제이력.set금액(Double.valueOf(get가격()));

        Application.applicationContext.getBean(fooddelivery.external.결제이력Service.class)
                .결제(결제이력);


    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    public Integer get수량() {
        return 수량;
    }

    public void set수량(Integer 수량) {
        this.수량 = 수량;
    }

    public String get상태() {
        return 상태;
    }

    public void set상태(String 상태) {
        this.상태 = 상태;
    }

    public String get가게() {
        return 가게;
    }

    public void set가게(String 가게) {
        this.가게 = 가게;
    }

    public Long get가격() {
        return 가격;
    }

    public void set가격(Long 가격) {
        this.가격 = 가격;
    }
}
