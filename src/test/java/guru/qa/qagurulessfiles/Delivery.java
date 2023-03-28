package guru.qa.qagurulessfiles;

import org.junit.jupiter.api.Tag;

import java.util.List;

public class Delivery {
    public String courier;
    public Address address;
    public Integer id;
    public List<Product> products;

    public static class Address{
        public String city;
        public String street;
        public Integer number;
        public List<String>messages;
    }

    public static class Product{
        public String goods;
        public Double weight;
    }
}
