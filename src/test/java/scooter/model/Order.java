package scooter.model;

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
        Random random = new Random();

        String[] firstNames = {"Naruto", "Sasuke", "Kakashi", "Sakura", "Hinata"};
        String[] lastNames = {"Uchiha", "Haruno", "Hatake", "Namikaze", "Nara"};
        String[] addresses = {"Konoha, 142 apt.", "Suna, 58 apt.", "Kiri, 99 apt.", "Iwa, 25 apt.", "Konoha, 99 apt."};
        String[] phoneNumbers = {"+7 800 355 35 35", "+7 800 123 45 67", "+7 800 765 43 21", "+7 800 987 65 43"};
        String[] comments = {"Sasuke, come back to Konoha", "I am looking for Naruto", "My name is Sakura", "Please be careful with my order"};
        String[] deliveryDates = {"2020-06-06", "2021-05-15", "2022-04-10", "2023-03-20"};
        List<String> colors = List.of("BLACK", "GREY");

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String address = addresses[random.nextInt(addresses.length)];
        int metroStation = random.nextInt(10) + 1;
        String phone = phoneNumbers[random.nextInt(phoneNumbers.length)];
        int rentTime = random.nextInt(12) + 1;
        String deliveryDate = deliveryDates[random.nextInt(deliveryDates.length)];
        String comment = comments[random.nextInt(comments.length)];

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
