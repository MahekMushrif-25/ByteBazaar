import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementSystem extends JFrame {

    private static final long serialVersionUID = 1L;

    static int failure = 0;
    static int success = 0;

    static class Product {
        private int prod_code;
        private String prod_company;
        private String prod_name;
        private double price;
        private String prod_type;
        private int stock;

        Product(int u, String x, String y, String z, double w, int v) {
            prod_code = u;
            prod_name = x;
            prod_company = y;
            prod_type = z;
            price = w;
            stock = v;
        }

        Product(String x, String y) {
            prod_company = x;
            prod_type = y;
        }

        int search(Product x) {
            return prod_company.equals(x.prod_company) && prod_type.equals(x.prod_type) ? 1 : 0;
        }

        void noOfcopies(int required) {
            if (required > stock) {
                JOptionPane.showMessageDialog(null, "Required copies are not in stock");
                Trans(0);
            } else {
                JOptionPane.showMessageDialog(null, "Total cost of the product: " + required * price +
                        "\nRemaining stock: " + (stock - required));
                stock = stock - required;
                Trans(1);
            }
        }

        void updatePrice(double newPrice) {
            price = newPrice;
        }

        void updateStock(int newStock) {
            stock = newStock;
        }

        Object[] getProductData() {
            return new Object[]{prod_code, prod_company, prod_name, prod_type, price, stock};
        }
    }

    static void Trans(int a) {
        if (a == 0)
            failure++;
        else
            success++;
    }

    static Product p1 = new Product(111, "Iphone14", "Apple", "Phone", 150000, 10);
    static Product p2 = new Product(112, "EOS90D", "Canon", "Camera", 120000, 15);
    static Product p3 = new Product(113, "Victus", "HP", "Laptop", 175000, 7);
    static Product p4 = new Product(114, "G102Prodigy", "Logitech", "Mouse", 2000, 30);
    static Product p5 = new Product(115, "Series8", "Apple", "SmartWatch", 85000, 18);

    private DefaultTableModel tableModel;

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JPanel sellerPanel = createSellerPanel();
        JPanel customerPanel = createCustomerPanel();

        tabbedPane.addTab("Seller", sellerPanel);
        tabbedPane.addTab("Customer", customerPanel);

        JPanel productPanel = createProductPanel();
        tabbedPane.addTab("Products", productPanel);
    }

    private JPanel createSellerPanel() {
        JPanel sellerPanel = new JPanel();
        sellerPanel.setLayout(new GridLayout(4, 1));

        JButton displayProductsBtn = new JButton("Display all Products");
        displayProductsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) InventoryManagementSystem.this.getContentPane().getComponent(0);
                tabbedPane.setSelectedIndex(2); // Set index to the "Products" tab
            }
        });
        sellerPanel.add(displayProductsBtn);

        JButton updateProductsBtn = new JButton("Update");
        updateProductsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int productCode = Integer.parseInt(JOptionPane.showInputDialog("Enter the Product code which has to be updated: "));
                String choice = JOptionPane.showInputDialog("Select what to update\n1. Price\n2. Stock");

                if (choice.equals("1")) {
                    double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter the new price: "));
                    switch (productCode) {
                        case 111:
                            p1.updatePrice(newPrice);
                            break;
                        case 112:
                            p2.updatePrice(newPrice);
                            break;
                        case 113:
                            p3.updatePrice(newPrice);
                            break;
                        case 114:
                            p4.updatePrice(newPrice);
                            break;
                        case 115:
                            p5.updatePrice(newPrice);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid Product code");
                    }
                } else if (choice.equals("2")) {
                    int newStock = Integer.parseInt(JOptionPane.showInputDialog("Enter the new stock: "));
                    switch (productCode) {
                        case 111:
                            p1.updateStock(newStock);
                            break;
                        case 112:
                            p2.updateStock(newStock);
                            break;
                        case 113:
                            p3.updateStock(newStock);
                            break;
                        case 114:
                            p4.updateStock(newStock);
                            break;
                        case 115:
                            p5.updateStock(newStock);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid Product code");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid choice");
                }
                updateProductTable();
            }
        });
        sellerPanel.add(updateProductsBtn);

        JButton viewTransactionsBtn = new JButton("View Transactions");
        viewTransactionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Total failed transaction: " + failure + "\nTotal successful transaction: " + success);
            }
        });
        sellerPanel.add(viewTransactionsBtn);

        JButton exitSellerBtn = new JButton("Exit");
        exitSellerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        sellerPanel.add(exitSellerBtn);

        return sellerPanel;
    }

    private JPanel createCustomerPanel() {
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(4, 1));

        JButton displayProductsBtn = new JButton("Display all Products");
        displayProductsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) InventoryManagementSystem.this.getContentPane().getComponent(0);
                tabbedPane.setSelectedIndex(2); // Set index to the "Products" tab
            }
        });
        customerPanel.add(displayProductsBtn);

        JButton purchaseProductBtn = new JButton("Purchase a Product");
        purchaseProductBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String prodCompany = JOptionPane.showInputDialog("Enter Product Company: ");
                String prodType = JOptionPane.showInputDialog("Enter Product Type: ");
                Product product = new Product(prodCompany, prodType);

                if (product.search(p1) == 1)
                    purchaseProduct(p1);
                else if (product.search(p2) == 1)
                    purchaseProduct(p2);
                else if (product.search(p3) == 1)
                    purchaseProduct(p3);
                else if (product.search(p4) == 1)
                    purchaseProduct(p4);
                else if (product.search(p5) == 1)
                    purchaseProduct(p5);
                else
                    JOptionPane.showMessageDialog(null, "This product is not available");
                updateProductTable();
            }
        });
        customerPanel.add(purchaseProductBtn);

        JButton exitCustomerBtn = new JButton("Exit");
        exitCustomerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        customerPanel.add(exitCustomerBtn);

        return customerPanel;
    }

    private JPanel createProductPanel() {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Product Code", "Company", "Name", "Type", "Price", "Stock"};
        Object[][] data = {
                {p1.prod_code, p1.prod_company, p1.prod_name, p1.prod_type, p1.price, p1.stock},
                {p2.prod_code, p2.prod_company, p2.prod_name, p2.prod_type, p2.price, p2.stock},
                {p3.prod_code, p3.prod_company, p3.prod_name, p3.prod_type, p3.price, p3.stock},
                {p4.prod_code, p4.prod_company, p4.prod_name, p4.prod_type, p4.price, p4.stock},
                {p5.prod_code, p5.prod_company, p5.prod_name, p5.prod_type, p5.price, p5.stock}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        productPanel.add(scrollPane, BorderLayout.CENTER);

        return productPanel;
    }

    private void purchaseProduct(Product product) {
        int required = Integer.parseInt(JOptionPane.showInputDialog("Enter required number of copies: "));
        product.noOfcopies(required);
    }

    private void updateProductTable() {
        tableModel.setValueAt(p1.stock, 0, 5);
        tableModel.setValueAt(p2.stock, 1, 5);
        tableModel.setValueAt(p3.stock, 2, 5);
        tableModel.setValueAt(p4.stock, 3, 5);
        tableModel.setValueAt(p5.stock, 4, 5);
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();
        ims.setVisible(true);
    }
}