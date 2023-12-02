package com.LogisticsCompany.model;

public class OrderBuilder {

    private Client sender;
    private Client receiver;
    private String receiverAddress;
    private double price;
    private DeliveryType deliveryType;
    private DeliveryStatus status;
    private Office office;

    public OrderBuilder withSender(Client sender) {
        this.sender = sender;
        return this;
    }

    public OrderBuilder withReceiver(Client receiver) {
        this.receiver = receiver;
        return this;
    }

    public OrderBuilder withReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
        return this;
    }

    public OrderBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public OrderBuilder withDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public OrderBuilder withStatus(DeliveryStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder withOffice(Office office) {
        this.office = office;
        return this;
    }

    // Additional methods that the builder supports, for example:
    public OrderBuilder clientIsSender() {
        // Logic to set the client as the sender
        return this;
    }

    public OrderBuilder clientIsReceiver() {
        // Logic to set the client as the receiver
        return this;
    }

    public Order createResult() {
        // Here you would create an Order object using the provided details.
        // Since we don't have the full logic or constructors, this is a simplification.
        Order order = new Order();
        order.setSender(this.sender);
        order.setReceiver(this.receiver);
        order.setReceiverAddress(this.receiverAddress);
        order.setPrice(this.price);
        order.setDeliveryType(this.deliveryType);
        order.setStatus(this.status);
        order.setOffice(this.office);
        // Further initialization can be done here.
        return order;
    }
}