package com.asdc.dalexchange.util;

public class NotificationUtil {

    public static String getTitle(String status) {
        return switch (status.toLowerCase()) {
            case "approved" -> "Buy Request Approved";
            case "rejected" -> "Buy Request Rejected";
            case "completed" -> "Purchase Completed";
            case "canceled" -> "Buy Request canceled.";
            default -> "";
        };
    }

    public static String getMessage(String status, String productName) {
        return switch (status.toLowerCase()) {
            case "approved" -> "Your buy request for product " + productName + " has been approved.";
            case "rejected" -> "Your buy request for product " + productName + " has been rejected.";
            case "completed" -> "Congratulations on your new purchase. Your payment for product " + productName + " has been received.";
            case "canceled" -> "The buy request for product " + productName + " has been canceled.";
            default -> "";
        };
    }

    public static String getSellerTitle(String status) {
        return switch (status.toLowerCase()) {
            case "approved" -> "Buy Request Approved";
            case "rejected" -> "Buy Request Rejected";
            case "completed" -> "Product Sold";
            case "canceled" -> "Request canceled";
            default -> "";
        };
    }

    public static String getSellerMessage(String status, String productName) {
        return switch (status.toLowerCase()) {
            case "completed" -> "Congratulations! Your product is sold. The buyer has completed the payment for your product " + productName + ".";
            case "canceled" -> "The payment request for your product " + productName + " has been canceled.";
            default -> "";
        };
    }
}
