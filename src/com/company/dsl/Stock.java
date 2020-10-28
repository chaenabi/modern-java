package com.company.dsl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.company.dsl.MethodChainingOrderBuilder.forCustomer;
import static org.assertj.core.api.Assertions.assertThat;

@Getter @Setter
public class Stock {
    private String symbol;
    private String market;
}

@Getter
@Setter
class Trade {
    public enum Type { BUY, SELL }
    private Type type;
    private Stock stock;
    private int quantity;
    private double price;

    public double getValue() {
        return quantity * price;
    }
}

class Order {
    private String customer;
    private final List<Trade> trades = new ArrayList<>();

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}

class MethodChainingOrderBuilder {
    public final Order order = new Order();

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }
    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }
    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade);
        return this;
    }

    public Order end() {
        return order;
    }
}

class TradeBuilder {
    private final MethodChainingOrderBuilder builder;
    public final Trade trade = new Trade();
    TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
        this.builder = builder;
        trade.setType(type);
        trade.setQuantity(quantity);
    }

    public StockBuilder stock(String symbol) {
        return new StockBuilder(builder, trade, symbol);
    }
}

class StockBuilder {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    private final Stock stock = new Stock();

    StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
        this.builder = builder;
        this.trade = trade;
        stock.setSymbol(symbol);
    }

    public TradeBuilderWithStock on(String market) {
        stock.setMarket(market);
        trade.setStock(stock);
        return new TradeBuilderWithStock(builder, trade);
    }
}

@RequiredArgsConstructor
class TradeBuilderWithStock {
    private final MethodChainingOrderBuilder builder;
    private final Trade trade;
    public MethodChainingOrderBuilder at(double price) {
        trade.setPrice(price);
        return builder.addTrade(trade);
    }
}


class StockOrderTest {

    public static void main(String[] args) {
      new StockOrderTest().orderTest();
    }
    @Test
    public void orderTest() {
        // given
        Order order = forCustomer("BigBank")
                        .buy(80)
                        .stock("IBM")
                        .on("NYSE")
                        .at(125.00)
                        .sell(50)
                        .stock("GOOGLE")
                        .on("NASDAQ")
                        .at(375.00)
                        .end();
        //then
        assertThat(order.getCustomer()).isEqualTo("BigBank");
        assertThat(order.getValue()).isSameAs(10000);
    }
}






