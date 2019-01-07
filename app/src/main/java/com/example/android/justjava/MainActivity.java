package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 99) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Figure out Name f user
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants Biscuits
        CheckBox BiscuitsCheckBox = (CheckBox) findViewById(R.id.biscuits_checkbox);
        boolean hasBiscuits = BiscuitsCheckBox.isChecked();

        // Figure out if the user wants Smoosas
        CheckBox SmosaCheckBox = (CheckBox) findViewById(R.id.smosas_checkbox);
        boolean hasSmosa = SmosaCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasBiscuits, hasSmosa);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasBiscuits, hasSmosa);
        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invoice for respected " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); }
        }

    /* Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean AddBiscuits, boolean AddSmoosa) {
        int basePrice = 25;
        // If user wants biscuits
        if (AddBiscuits) {
            basePrice = basePrice + 15;
        }
        // If user wants samosa
        if (AddSmoosa) {
            basePrice = basePrice + 10;
        }
        // total price
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     * Name of the customer
     * price of the order
     * is whether or not to add whipped cream to the coffee
     * is whether or not to add chocolate to the coffee
     * text summary
     */
    private String createOrderSummary(String name, int price, boolean addBiscuits, boolean addSmosa) {
        String priceMessage = "      TJ CAFE \n";
        priceMessage += "\n Name:                           " + name;
        priceMessage += "\n Biscuits Included:        " + addBiscuits;
        priceMessage += "\n Smoosas Included:      " + addSmosa;
        priceMessage += "\n Tea/Coffee Quantity:   " + quantity;
        priceMessage += "\n Total:                              " +"Rs "+ price;
        priceMessage += "\n\n   Thank you! ";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }



}