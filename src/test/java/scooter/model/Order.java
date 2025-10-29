package scooter.model;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.Random;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order() {
    }

    public static Order getRandom() {
        Faker faker = new Faker();
        Random random = new Random();

        List<String> colors = List.of("BLACK", "GREY");

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().fullAddress();
        int metroStation = random.nextInt(10) + 1;
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = random.nextInt(12) + 1;

        String deliveryDate = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate()
                .toString();

        String comment = faker.lorem().sentence();

        int colorCount = random.nextInt(3);
        List<String> color;
        if (colorCount == 0) {
            color = List.of();
        } else if (colorCount == 1) {
            color = List.of(colors.get(random.nextInt(colors.size())));
        } else {
            color = List.of("BLACK", "GREY");
        }

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }



    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getMetroStation() { return metroStation; }
    public void setMetroStation(int metroStation) { this.metroStation = metroStation; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getRentTime() { return rentTime; }
    public void setRentTime(int rentTime) { this.rentTime = rentTime; }
    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public List<String> getColor() { return color; }
    public void setColor(List<String> color) { this.color = color; }
}
