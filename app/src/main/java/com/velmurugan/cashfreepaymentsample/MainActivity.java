package com.velmurugan.cashfreepaymentsample;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_BANK_CODE;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_OPTION;
import static com.cashfree.pg.CFPaymentService.PARAM_UPI_VPA;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cashfree.pg.CFPaymentService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String stage = "TEST";
    String clientId = "8759726ba4d81c7253bfcd53679578";
    String clientSecret = "c1aabd5e2a313986fd862c82f18c919c6bacf759";
    String amount;

    Button btnWebCheckout, btnUpi, btnNetBanking;
    EditText edAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edAmount = (EditText) findViewById(R.id.edInput);
        btnUpi = findViewById(R.id.btnUpiPayment);
        btnNetBanking = findViewById(R.id.btbNetBankingPayment);
        btnWebCheckout = findViewById(R.id.btnWebCheckout);

        btnWebCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = edAmount.getText().toString();

                OrderTokenRequest orderTokenRequest = new OrderTokenRequest();
                orderTokenRequest.setOrderAmount(amount);
                orderTokenRequest.setOrderId(amount);
                orderTokenRequest.setOrderCurrency("INR");

                ApiService.getInstance().getOrderToken(clientId, clientSecret, orderTokenRequest).enqueue(new Callback<OrderTokenResponse>() {
                    @Override
                    public void onResponse(Call<OrderTokenResponse> call, Response<OrderTokenResponse> response) {
                        Log.d("onResponse", "onResponse: " + response.body());

                        OrderTokenResponse orderTokenResponse = response.body();

                        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);
                        cfPaymentService.doPayment(MainActivity.this, getWebCheckoutInputParams(), orderTokenResponse.getCftoken(), stage);

                    }

                    @Override
                    public void onFailure(Call<OrderTokenResponse> call, Throwable t) {
                        Log.d("onFailure", "onResponse: " + t.getMessage());

                    }

                });
            }
        });

        btnUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = edAmount.getText().toString();

                OrderTokenRequest orderTokenRequest = new OrderTokenRequest();
                orderTokenRequest.setOrderAmount(amount);
                orderTokenRequest.setOrderId(amount);
                orderTokenRequest.setOrderCurrency("INR");

                ApiService.getInstance().getOrderToken(clientId, clientSecret, orderTokenRequest).enqueue(new Callback<OrderTokenResponse>() {
                    @Override
                    public void onResponse(Call<OrderTokenResponse> call, Response<OrderTokenResponse> response) {
                        Log.d("onResponse", "onResponse: " + response.body());

                        OrderTokenResponse orderTokenResponse = response.body();

                        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);
                        cfPaymentService.doPayment(MainActivity.this, getUpiInputParams(), orderTokenResponse.getCftoken(), stage);


                    }

                    @Override
                    public void onFailure(Call<OrderTokenResponse> call, Throwable t) {
                        Log.d("onFailure", "onResponse: " + t.getMessage());

                    }

                });
            }
        });

        btnNetBanking.setOnClickListener(v -> {
            amount = edAmount.getText().toString();

            OrderTokenRequest orderTokenRequest = new OrderTokenRequest();
            orderTokenRequest.setOrderAmount(amount);
            orderTokenRequest.setOrderId(amount);
            orderTokenRequest.setOrderCurrency("INR");

            ApiService.getInstance().getOrderToken(clientId, clientSecret, orderTokenRequest).enqueue(new Callback<OrderTokenResponse>() {
                @Override
                public void onResponse(Call<OrderTokenResponse> call, Response<OrderTokenResponse> response) {
                    Log.d("onResponse", "onResponse: " + response.body());

                    OrderTokenResponse orderTokenResponse = response.body();
                    Activity activity = MainActivity.this;
                    CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                    cfPaymentService.setOrientation(0);
                    cfPaymentService.doPayment(activity
                            , getInputParams(), orderTokenResponse.getCftoken(), stage, "#121222", "#343444");


                }

                @Override
                public void onFailure(Call<OrderTokenResponse> call, Throwable t) {
                    Log.d("onFailure", "onResponse: " + t.getMessage());

                }

            });

        });
    }

    private Map<String, String> getWebCheckoutInputParams() {
        String orderId = amount;
        String orderAmount = amount;
        String orderNote = "Test Order 7";
        String customerName = "John 56r";
        String customerPhone = "9900012343";
        String customerEmail = "test2@gmail.com";
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, clientId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        return params;
    }


    private Map<String, String> getUpiInputParams() {
        String orderId = amount;
        String orderAmount = amount;
        String orderNote = "Test Order 7";
        String customerName = "John 56r";
        String customerPhone = "9900012343";
        String customerEmail = "test2@gmail.com";
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, clientId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        params.put(PARAM_PAYMENT_OPTION, "upi");
        params.put("returnUrl","https://www.test.com/test");
        params.put(PARAM_NOTIFY_URL,"https://www.test.com/test");
        params.put(PARAM_UPI_VPA, "testsuccess@gocash");
        return params;
    }
    private Map<String, String> getInputParams() {
        String orderId = amount;
        String orderAmount = amount;
        String orderNote = "Test Ord r7";
        String customerName = "John er";
        String customerPhone = "9900012643";
        String customerEmail = "test25@gmail.com";
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, clientId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        params.put(PARAM_PAYMENT_OPTION, "nb");
        params.put(PARAM_BANK_CODE, "3003");
        return params;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }
        }
    }
}