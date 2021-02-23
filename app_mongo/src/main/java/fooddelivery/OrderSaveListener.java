package fooddelivery;

import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;

/**
 * Equivalent of a domain method annotated by <code>PrePersist</code>.
 * <p/>
 * This handler shows how to implement your custom UUID generation.
 * @author Tobias Trelle
 */
public class OrderSaveListener extends AbstractMongoEventListener<Order>  {

	@Override
	public void onBeforeSave(BeforeSaveEvent<Order> event) {
		Order order = event.getSource();
		Document d = event.getDocument();
		
		if ( order.getId() == null ) {
			// TODO use a better UUID generator in production
			d.put("_id","" + Math.random() );
		}
		
	}
	
	@Override
    public void onAfterSave(AfterSaveEvent<Order> event){
		Order ordr = event.getSource();
		
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        fooddelivery.external.결제이력 결제이력 = new fooddelivery.external.결제이력();

        // this is Context Mapping (Anti-corruption Layer)
        결제이력.setOrderId(String.valueOf(ordr.getId()));
        if(ordr.get가격()!=null)
            결제이력.set금액(Double.valueOf(ordr.get가격()));

        Application.applicationContext.getBean(fooddelivery.external.결제이력Service.class)
                .결제(결제이력);


    }

}