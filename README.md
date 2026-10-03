# Supermarket Billing System (Java)

[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![Case Study](https://img.shields.io/badge/Academic-Case%20Study%20143-blue.svg)]()
[![License](https://img.shields.io/badge/License-MIT-green.svg)]()

> A console-based Supermarket Billing and Inventory Invoicing System in Java, demonstrating procedural architecture, parallel arrays, tiered discount logic, and tax computation. Developed for **Case Study 143: Supermarket Billing System**.

---

## 🏛️ System Architecture

[![Architecture diagram of kushal-sw/billing-system-java](https://gitdiagram.com/kushal-sw/billing-system-java/diagram.png)](https://gitdiagram.com/kushal-sw/billing-system-java?utm_source=readme&utm_medium=picture)

---

## 📋 Problem Statement & Objectives

Supermarkets require a computerized billing system to calculate customer purchases, discounts, taxes, and final payable amounts.

### Key Objectives:
- **Accept Product Details & Quantities**: Interactive selection and robust quantity inputs.
- **Item-Wise & Total Costing**: Calculate line-item prices, subtotal, and individual GST amounts.
- **Tiered Discounts (`if-else`)**: Apply automated discount slabs based on the total purchase amount.
- **Product Classification (`switch-case`)**: Category resolution across Grocery, Dairy, Snacks, Beverages, and Personal Care.
- **Formatted Invoice Generation**: Output a structured, itemized tax invoice.

---

## 🧩 The 5 Core Modules

| Module | Method | Description |
|---|---|---|
| **Module 1: Product Selection** | `displayProducts()` | Displays the catalogue with prices, GST slabs, and category names resolved via `switch-case`. |
| **Module 2: Quantity Entry** | `addToCart(productIdx, qty)` | Adds items to cart; automatically merges quantities if the item already exists in the cart. |
| **Module 3: Bill Calculation** | `calculateSubtotal()` | Iterates through the cart to compute the pre-tax gross subtotal from catalog prices. |
| **Module 4: Discount Calculation** | `getDiscountPercent()`, `calculateDiscount()` | Multi-branch `if-else` ladder applying tiered discounts based on the purchase amount. |
| **Module 5: Invoice Generation** | `generateInvoice(customerName)` | Formats the final bill, calculates post-discount GST, and outputs the receipt using `printf`. |

---

## 💡 Core Java Concepts Implemented

- **Parallel Arrays**: Coordinates `productNames`, `productPrices`, `productGst`, and `productCategories` across shared indices without unnecessary object bloat.
- **Memory-Optimized Cart**: Tracks purchases using only `cartProductIndex` and `cartQty`, referencing the catalog as the single source of truth.
- **Control Structures**:
  - `do-while`: Interactive continuous shopping loop.
  - `while`: Safe input validation loops reprompting on errors.
  - `switch-case`: Integer category code mapping to department names.
  - `if-else if-else`: Tiered discount rate qualification.
- **Input Stream Guarding**: Custom `readInt()` helper utilizing `Scanner.hasNextInt()` to prevent `InputMismatchException` crashes when users enter letters.
- **Precise Formatted Output**: Uses `System.out.printf` with strict column width specifiers (`%-22s`, `%8.2f`, `%5.0f%%`).

---

## 💰 Discount & Tax Policy

### Discount Slabs (Applied on Gross Subtotal via `if-else`):
* **$\ge$ ₹5,000:** 15% discount
* **$\ge$ ₹3,000:** 10% discount
* **$\ge$ ₹1,000:** 5% discount
* **Below ₹1,000:** 0% (No discount)

### Tax (GST) Calculation:
In accordance with standard commercial billing rules, GST is charged on the **net discounted price** rather than pre-discount MRP:
$$\text{Line Net Amount} = \text{Line Total} \times \left(1 - \frac{\text{Discount \%}}{100}\right)$$
$$\text{Line GST} = \text{Line Net Amount} \times \left(\frac{\text{Product GST \%}}{100}\right)$$

---

## 🚀 Getting Started

### Prerequisites
* **Java Development Kit (JDK 8 or later)** installed.
* Terminal / Command Prompt.

### Compilation & Execution
```bash
# Clone the repository
git clone https://github.com/kushal-sw/Billing-system-JAVA.git
cd Billing-system-JAVA

# Compile the Java source code
javac SupermarketBilling.java

# Run the billing application
java SupermarketBilling
```

---

## 🖥️ Sample Terminal Execution

```text
*** WELCOME TO SUPER MART BILLING SYSTEM ***
Enter customer name: Kushal

===================== PRODUCT CATALOGUE =====================
No.  Product                Category           Price   GST%
-------------------------------------------------------------
1    Rice (1 kg)            Grocery            70.00     5%
2    Wheat Flour (1 kg)     Grocery            45.00     5%
3    Milk (1 L)             Dairy              60.00     0%
4    Cheese (200 g)         Dairy             120.00     5%
5    Chips                  Snacks             20.00     5%
6    Cold Drink (750 ml)    Beverages          40.00    28%
7    Soap                   Personal Care      35.00     5%
8    Shampoo                Personal Care     180.00     5%
-------------------------------------------------------------
Enter product number (1-8): 1
Enter quantity: 2
-> Added: Rice (1 kg) x 2

Add more items? (y/n): y
...
Enter product number (1-8): 8
Enter quantity: 20
-> Added: Shampoo x 20

Add more items? (y/n): n

==================================================================
                      SUPER MART - TAX INVOICE
==================================================================
Customer : Kushal
(Note: GST is computed on item amount after applied discount)
------------------------------------------------------------------
No.  Item                    Price  GST%  Qty     Amount   GST Amt
------------------------------------------------------------------
1    Rice (1 kg)             70.00    5%    2     140.00      6.30
2    Shampoo                180.00    5%   20    3600.00    162.00
------------------------------------------------------------------
Subtotal:                                              3740.00
Discount (10% on purchase amount):                     -374.00
Total GST:                                              168.30
------------------------------------------------------------------
FINAL PAYABLE AMOUNT (Rs.):                            3534.30
==================================================================
               Thank you for shopping with us!
```

---

## 📂 Project Structure

```
Billing-system-JAVA/
├── SupermarketBilling.java           # Main Java source code containing all 5 modules
├── SUPERMARKET_BILLING_VIVA_GUIDE.md # Detailed Line-by-line Viva & concepts guide
└── README.md                         # Project documentation and architecture
```

---

## 👨‍💻 Author
- **Kushal** ([@kushal-sw](https://github.com/kushal-sw))
- B.Tech CSE (Semester III) — ITM Skills University
