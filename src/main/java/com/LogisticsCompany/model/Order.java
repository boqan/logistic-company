package com.LogisticsCompany.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Make no-args constructor protected
@AllArgsConstructor(access = AccessLevel.PRIVATE) // Make all-args constructor private
@Builder
@Entity
@Table(name = "orders")
public class Order extends IdGenerator {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Client sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false, length = 255)
    private String receiverAddress;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;

    // Lombok's @Builder will automatically implement the builder pattern
    // with a static inner class "OrderBuilder" and will include all fields from the Order class.


    public static class OrderBuilder {
        // Custom builder method
        public OrderBuilder clientIsSender(Client client) {
            this.sender = client;
            return this;
        }

        public OrderBuilder clientIsReceiver(Client client) {
            this.receiver = client;
            return this;
        }

        private double calculatePriceBasedOnDeliveryDetails(double weight, String receiverAddress, DeliveryType deliveryType) {
            // Placeholder for price calculation logic
            double basePrice = 10.0;
            double distanceFactor = 1.0; // You would have logic to calculate this
            double weightFactor = 2.0; // And logic to determine this

            // Example calculation
            return basePrice + (weight * weightFactor) + (distanceFactor);
        }

        public OrderBuilder withDeliveryAddress(String address) {
            this.receiverAddress = address;
            // Assuming weight and deliveryType are already set
            this.price = calculatePriceBasedOnDeliveryDetails(this.weight, address, this.deliveryType);
            return this;
        }

        // Note: The build() method is not needed here, Lombok generates it for you.
        // If you need to customize the build process, you can add a method build() here,
        // and Lombok will use your method instead of generating its own.
    }
}
