package com.mycompany.advertisementmanagementsystem;

import advertisement.Advertisement;
import advertisement.Advertisement.AdPlacement;
import advertiser.Advertiser;
import contributors.Journalist;
import contributors.Photographer;
import contributors.Story;
import contributors.Photograph;
import payment.Accounts;
import payment.Invoice;
import system.SystemWorkFlow;
import user.MarketingStaff;
import user.EditingDepartment;
import java.util.List;

public class AdvertisementManagementSystem {

    public static void main(String[] args) {
        try {
            System.out.println("=== Advertisement Management System Started ===\n");

            // Initialize system components
            System.out.println("Initializing system components...");
            SystemWorkFlow workflow = new SystemWorkFlow();
            Accounts accounts = new Accounts();
            System.out.println("System components initialized successfully.\n");

            // Create advertisers
            System.out.println("Creating advertisers...");
            Advertiser advertiser1 = new Advertiser(1, "Advertiser One", "contact@advertiser1.com");
            Advertiser advertiser2 = new Advertiser(2, "Advertiser Two", "contact@advertiser2.com");
            System.out.println("Advertisers created: " + advertiser1.getName() + ", " + advertiser2.getName() + "\n");

            // Create advertisements
            System.out.println("Creating advertisements...");
            Advertisement ad1 = createAdvertisement(101, "Full Page", "FRONT_COVER", advertiser1);
            Advertisement ad2 = createAdvertisement(102, "Half Page", "BACK_COVER", advertiser2);
            Advertisement ad3 = createAdvertisement(103, "Quarter Page", "INSIDE_PAGE", advertiser1);
            System.out.println("Advertisements created successfully.\n");

            // Add advertisements to advertisers and workflow
            System.out.println("Adding advertisements to advertisers and workflow...");
            advertiser1.placeAdvertisement(ad1);
            advertiser2.placeAdvertisement(ad2);
            advertiser1.placeAdvertisement(ad3);

            workflow.addAdvertisement(ad1);
            workflow.addAdvertisement(ad2);
            workflow.addAdvertisement(ad3);
            System.out.println("Advertisements added successfully.\n");

            System.out.println("--- Processing Advertisements ---");

            // Marketing Staff processes ads
            MarketingStaff marketingStaff = new MarketingStaff(3, "marketer", "password", "Marketing", workflow);
            System.out.println("Marketing staff initiating ad processing...");
            marketingStaff.initiateAdProcessing();

            List<Advertisement> ads = marketingStaff.requestAdDetails("Pending");
            marketingStaff.receiveConfirmation(ads);
            System.out.println("Advertisements processed by Marketing Staff.\n");

            // Editing Department reviews ads
            EditingDepartment editingDepartment = new EditingDepartment(4, "editor", "securepass", "Editor");
            System.out.println("Editing department reviewing advertisements...");
            ads.forEach(ad -> editingDepartment.reviewAd(ad));
            System.out.println("Advertisements reviewed by Editing Department.\n");

            // Mark ads as ready for payment if approved by both the advertiser and editing department
            ads.forEach(ad -> {
                if (ad != null && ad.isReviewedByEditing() && ad.isConfirmedByAdvertiser()) {
                    ad.setReadyForPayment(true);
                    System.out.println("Ad ID: " + ad.getId() + " is ready for payment.");
                }
            });

            // Archive unapproved ads
            System.out.println("Archiving unused submissions...");
            workflow.archiveUnusedSubmissions();
            System.out.println("Unused submissions archived.\n");

            System.out.println("--- Payment Processing ---");

            // Process individual payments
            System.out.println("Processing payments for individual advertisements...");
            processPayment(accounts, ad1);
            processPayment(accounts, ad2);

            // Create an invoice and process batch payment
            System.out.println("Issuing an invoice for Advertiser One...");
            Invoice invoice = accounts.issueInvoice(advertiser1, 300); // Example invoice amount
            System.out.println("Invoice ID " + invoice.getId() + " issued for Advertiser One.");

            System.out.println("Processing batch payments...");
            List<Advertisement> batchAds = List.of(ad1, ad3);
            processBatchPayment(accounts, batchAds);

            System.out.println("\n--- Handling Late Payments ---");
            if (!accounts.handleLatePayments()){
                System.out.println("No late payments found.");
            } else {
                System.out.println("Late payments handled.\n");
            }

            System.out.println("--- Contributors Workflow ---");

            // Contributors submit content
            Journalist journalist = new Journalist(5, "Garima", 500);
            Photographer photographer = new Photographer(6, "Jain", 300);

            System.out.println("Contributors submitting content...");
            journalist.submitStory();
            photographer.submitStory();

            Story story = journalist.writeStory(1, "The Ad Revolution", "Exploring the future of AI.");
            Photograph photo = photographer.takePhoto(1, "Advertising in the Modern Age", "1920x1080");

            System.out.println("Journalist created a story: " + story.getTitle());
            System.out.println("Photographer submitted a photo: " + photo.getCaption());

            System.out.println("Calculating costs for story and photograph...");
            double storyCost = story.calculateCost();
            double photoCost = photo.calculateCost();

            System.out.println("Cost for the story: $" + storyCost);
            System.out.println("Cost for the photograph: $" + photoCost);

            // Process contributor payments
            System.out.println("Processing payment for contributors...");
            processContributorPayment(accounts, journalist, storyCost);
            processContributorPayment(accounts, photographer, photoCost);

            System.out.println("\n--- System Workflow Complete ---");

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private static Advertisement createAdvertisement(int id, String size, String placement, Advertiser advertiser) {
        AdPlacement adPlacement;
    try {
        adPlacement = AdPlacement.valueOf(placement.toUpperCase()); // Convert string to enum
    } catch (IllegalArgumentException e) {
        System.err.println("Error: Invalid placement value. Must be one of: TOP, BOTTOM, SIDEBAR, HEADER, FOOTER.");
        return null;
    }
        try {
            System.out.println("Creating advertisement with ID: " + id);
            return new Advertisement(id, size, adPlacement, advertiser);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Error creating advertisement: " + e.getMessage());
            return null;
        }
    }

    private static void processPayment(Accounts accounts, Advertisement ad) {
        try {
            System.out.println("Processing payment for Ad ID: " + (ad != null ? ad.getId() : "Unknown"));
            if (ad != null && ad.isReadyForPayment() && accounts.processIndependentPayment(ad)) {
                System.out.println("Payment processed successfully for Ad ID: " + ad.getId() +
                        " | Amount: $" + ad.calculateCost());
            } else {
                System.out.println("Payment failed for Ad ID: " + (ad != null ? ad.getId() : "Unknown"));
            }
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
        }
    }

    private static void processBatchPayment(Accounts accounts, List<Advertisement> ads) {
        try {
            System.out.println("Calculating batch payment total...");
            double batchTotal = ads.stream()
                    .filter(ad -> ad != null && ad.isReadyForPayment() && !ad.isArchived())
                    .mapToDouble(Advertisement::calculateCost)
                    .sum();

            System.out.println("Batch total calculated: $" + batchTotal);

            if (accounts.processBatchPayment(ads, batchTotal)) {
                System.out.println("Batch payment processed successfully.");
            } else {
                System.out.println("Batch payment failed: Some ads are not eligible for payment.");
            }
        } catch (Exception e) {
            System.err.println("Error processing batch payment: " + e.getMessage());
        }
    }

    private static void processContributorPayment(Accounts accounts, Object contributor, double amount) {
        try {
            if (accounts.processContributorPayment(contributor, amount)) {
                System.out.println("Payment processed successfully for " + contributor.getClass().getSimpleName() +
                        " | Amount: $" + amount);
            } else {
                System.out.println("Payment failed for " + contributor.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.err.println("Error processing contributor payment: " + e.getMessage());
        }
    }
}
