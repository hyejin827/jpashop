package jpabook.jpashop;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
//		Hello hello = new Hello();
//		hello.setData("hello");
//		String data = hello.getData();
//		System.out.println("data ============> " + data);
//
//		Javers javers = JaversBuilder.javers().build();
//		Hello model1 = new Hello();
//		model1.setData("data1-1");
//		model1.setData2("data1-2");
//		Hello model2 = new Hello();
//		model2.setData("data2-1");
//		model2.setData2("data2-2");
//		Diff diff = javers.compare(model1, model2);
//		System.out.println("diff ==> " + diff);
//		System.out.println("column ==> " + ((ValueChange) diff.getChanges().get(0)).getPropertyName());
//		System.out.println("model1 ==> " + ((ValueChange) diff.getChanges().get(0)).getLeft());
//		System.out.println("model2 ==> " + ((ValueChange) diff.getChanges().get(0)).getRight());

		SpringApplication.run(JpashopApplication.class, args);
	}

}
