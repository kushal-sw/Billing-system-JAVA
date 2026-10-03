import java.util.Scanner;

public class SupermarketBilling {

    // ==========================================
    // Product Catalogue (Parallel Arrays)
    // ==========================================
    static String[] productNames = {
        "Rice (1 kg)", "Wheat Flour (1 kg)", "Milk (1 L)", "Cheese (200 g)",
        "Chips", "Cold Drink (750 ml)", "Soap", "Shampoo"
    };
    static double[] productPrices = { 70.0, 45.0, 60.0, 120.0, 20.0, 40.0, 35.0, 180.0 };
    static double[] productGst    = { 5.0,  5.0,  0.0,   5.0,  5.0, 28.0,  5.0,   5.0 };
    // Category Codes: 1=Grocery, 2=Dairy, 3=Snacks, 4=Beverages, 5=Personal Care
    static int[] productCategories = { 1, 1, 2, 2, 3, 4, 5, 5 };

    // ==========================================
    // Cart Tracking (Index & Quantity Arrays)
    // ==========================================
    static final int MAX_ITEMS = 50;
    static final int MAX_QTY = 500;
    static int[] cartProductIndex = new int[MAX_ITEMS];
    static int[] cartQty = new int[MAX_ITEMS];
    static int cartCount = 0;

    // Switch Statement: Map category codes to names
    static String getCategoryName(int category) {
        switch (category) {
            case 1:  return "Grocery";
            case 2:  return "Dairy";
            case 3:  return "Snacks";
            case 4:  return "Beverages";
            case 5:  return "Personal Care";
            default: return "General";
        }
    }

    // Safe input helper to prevent crashes on non-numeric inputs
    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            }
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next(); // Discard invalid token
        }
    }

    // ==========================================
    // Module 1: Product Selection
    // ==========================================
    static void displayProducts() {
        System.out.println("\n===================== PRODUCT CATALOGUE =====================");
        System.out.printf("%-4s %-22s %-15s %8s %6s%n", "No.", "Product", "Category", "Price", "GST%");
        System.out.println("-------------------------------------------------------------");
        for (int i = 0; i < productNames.length; i++) {
            System.out.printf("%-4d %-22s %-15s %8.2f %5.0f%%%n",
                (i + 1), productNames[i], getCategoryName(productCategories[i]),
                productPrices[i], productGst[i]);
        }
        System.out.println("-------------------------------------------------------------");
    }

    // ==========================================
    // Module 2: Quantity Entry
    // ==========================================
    static boolean addToCart(int productIdx, int qty) {
        // If product already in cart, update existing quantity
        for (int i = 0; i < cartCount; i++) {
            if (cartProductIndex[i] == productIdx) {
                if (cartQty[i] + qty > MAX_QTY) {
                    System.out.println("Cannot add " + qty + ". Total quantity cannot exceed " + MAX_QTY + " (already in cart: " + cartQty[i] + ").");
                    return false;
                }
                cartQty[i] += qty;
                return true;
            }
        }

        // Add as new entry if cart has space
        if (cartCount < MAX_ITEMS) {
            cartProductIndex[cartCount] = productIdx;
            cartQty[cartCount] = qty;
            cartCount++;
            return true;
        } else {
            System.out.println("Cart limit reached (" + MAX_ITEMS + " items).");
            return false;
        }
    }

    // ==========================================
    // Module 3: Bill Calculation
    // ==========================================
    static double calculateSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < cartCount; i++) {
            int pIdx = cartProductIndex[i];
            subtotal += productPrices[pIdx] * cartQty[i];
        }
        return subtotal;
    }

    // ==========================================
    // Module 4: Discount Calculation
    // (if-else statements apply discounts based on purchase amount)
    // ==========================================
    static double getDiscountPercent(double subtotal) {
        if (subtotal >= 5000) {
            return 15.0; // 15% discount for Rs. 5000 and above
        } else if (subtotal >= 3000) {
            return 10.0; // 10% discount for Rs. 3000 to 4999
        } else if (subtotal >= 1000) {
            return 5.0;  // 5% discount for Rs. 1000 to 2999
        } else {
            return 0.0;  // No discount for purchases under Rs. 1000
        }
    }

    static double calculateDiscount(double subtotal, double discountPercent) {
        return subtotal * (discountPercent / 100.0);
    }

    // ==========================================
    // Module 5: Invoice Generation
    // ==========================================
    static void generateInvoice(String customerName) {
        double subtotal = calculateSubtotal();
        double discountPercent = getDiscountPercent(subtotal);
        double discountAmount = calculateDiscount(subtotal, discountPercent);
        double netMultiplier = 1.0 - (discountPercent / 100.0);
        double totalTax = 0;

        System.out.println("\n==================================================================");
        System.out.println("                      SUPER MART - TAX INVOICE");
        System.out.println("==================================================================");
        System.out.println("Customer : " + customerName);
        System.out.println("(Note: GST is computed on item amount after applied discount)");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-4s %-20s %8s %5s %4s %10s %9s%n",
            "No.", "Item", "Price", "GST%", "Qty", "Amount", "GST Amt");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < cartCount; i++) {
            int pIdx = cartProductIndex[i];
            int qty = cartQty[i];
            double lineTotal = productPrices[pIdx] * qty;
            double lineGst = (lineTotal * netMultiplier) * (productGst[pIdx] / 100.0);
            totalTax += lineGst;

            System.out.printf("%-4d %-20s %8.2f %4.0f%% %4d %10.2f %9.2f%n",
                (i + 1), productNames[pIdx], productPrices[pIdx], productGst[pIdx],
                qty, lineTotal, lineGst);
        }

        double finalAmount = (subtotal - discountAmount) + totalTax;

        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-49s %12.2f%n", "Subtotal:", subtotal);
        System.out.printf("%-49s %12.2f%n", "Discount (" + (int) discountPercent + "% on purchase amount):", -discountAmount);
        System.out.printf("%-49s %12.2f%n", "Total GST:", totalTax);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-49s %12.2f%n", "FINAL PAYABLE AMOUNT (Rs.):", finalAmount);
        System.out.println("==================================================================");
        System.out.println("               Thank you for shopping with us!");
    }

    // ==========================================
    // Main Method: Drives the Shopping Flow
    // ==========================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("*** WELCOME TO SUPER MART BILLING SYSTEM ***");
        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        char more;
        do {
            displayProducts();

            int choice = readInt(sc, "Enter product number (1-" + productNames.length + "): ");
            while (choice < 1 || choice > productNames.length) {
                System.out.println("Invalid product number. Please select from 1 to " + productNames.length + ".");
                choice = readInt(sc, "Enter product number (1-" + productNames.length + "): ");
            }

            int qty = readInt(sc, "Enter quantity: ");
            while (qty <= 0 || qty > MAX_QTY) {
                if (qty <= 0) {
                    System.out.println("Quantity must be greater than zero.");
                } else {
                    System.out.println("Quantity cannot exceed " + MAX_QTY + ".");
                }
                qty = readInt(sc, "Enter quantity: ");
            }

            if (addToCart(choice - 1, qty)) {
                System.out.println("-> Added: " + productNames[choice - 1] + " x " + qty);
            }

            System.out.print("\nAdd more items? (y/n): ");
            more = sc.next().charAt(0);

        } while (more == 'y' || more == 'Y');

        if (cartCount > 0) {
            generateInvoice(customerName);
        } else {
            System.out.println("\nNo items in cart. Invoice generation cancelled.");
        }

        sc.close();
    }
}