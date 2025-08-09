/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
package expensetracker;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a single expense record.
 * This class uses JavaFX properties to allow the TableView to automatically
 * update when the data changes.
 */
public class Expense {

    private final SimpleStringProperty description;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty category;

    public Expense(String description, double amount, String category) {
        this.description = new SimpleStringProperty(description);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
    }

    // Getters
    public String getDescription() {
        return description.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public String getCategory() {
        return category.get();
    }

    // Setters
    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    // Property getters - necessary for TableView integration
    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }
}
