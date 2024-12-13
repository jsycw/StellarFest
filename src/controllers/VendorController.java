package controllers;

import java.util.List;

import models.Event;
import models.Vendor;
import models.VendorProduct;
import utils.Auth;
import utils.Response;

public class VendorController {
    public Response<Void> acceptEventInvitation(String eventId) {
        Vendor loggedInVendor = (Vendor) Auth.get();

        if (loggedInVendor == null || !loggedInVendor.getRole().equals("Vendor")) {
            return Response.error("Error accepting invitation: user is not a vendor.");
        }

        return loggedInVendor.acceptEventInvitation(eventId);
    }

    public static Response<List<Event>> getAcceptedEvents(String email) {
        return Vendor.fetchAcceptedEvents(email);
    }

    public Response<Void> updateVendorProduct(String productDescription, String productName) {
        Vendor loggedInVendor = (Vendor) Auth.get();

        if (loggedInVendor == null || !loggedInVendor.getRole().equals("Vendor")) {
            return Response.error("Error managing vendor product: user is not a vendor.");
        }
        Response<Void> validationResponse = validateProductInput(productDescription, productName);
        if (!validationResponse.isSuccess()) {
            return validationResponse;
        }

        return loggedInVendor.manageVendorProduct(productDescription, productName);
    }

    public static Response<VendorProduct> getVendorProductDetails(String vendorId) {
        return Vendor.getVendorProductDetails(vendorId);
    }

    public static Response<Void> validateProductInput(String description, String productName) {
        if (description == null || description.isEmpty()) {
            return Response.error("Product description must be filled.");
        }
        if (productName == null || productName.isEmpty()) {
            return Response.error("Product name must be filled.");
        }
        if (description.length() > 200) {
            return Response.error("Product description must be 200 characters or less.");
        }
        return Response.success("Product input validation passed.", null);
    }
}
