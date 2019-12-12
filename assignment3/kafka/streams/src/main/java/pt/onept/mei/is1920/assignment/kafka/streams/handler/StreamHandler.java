package pt.onept.mei.is1920.assignment.kafka.streams.handler;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Windowed;

public final class StreamHandler {

	private final StreamOperationsHandler streamOperationsHandler;

	private KTable<Long, Float> expensePerItemTable = null;
	private KTable<Long, Float> revenuePerItemTable = null;
	private KTable<Long, Float> averageExpensePerItemTable = null;
	private KTable<Long, Float> profitPerItemTable = null;
	private KTable<Long, Float> totalRevenueTable = null;
	private KTable<Long, Float> totalExpenseTable = null;
	private KTable<Long, Float> averageExpensePerOrderTable = null;
	private KTable<Long, Float> totalProfitTable = null;
	private KTable<Long, Float> totalProfitLastHourTable = null;
	private KTable<Long, String> mostProfitableItemTable = null;
	private KTable<Long, String> countryHighestSalesTable = null;

	private KStream<Windowed<Long>, Float> totalRevenueLastHourStream = null;
	private KStream<Windowed<Long>, Float> totalExpenseLastHourStream = null;

	public StreamHandler(KStream<Long, String> ordersStream, KStream<Long, String> salesStream) {
		this.streamOperationsHandler = new StreamOperationsHandler(ordersStream, salesStream);
	}

	public KTable<Long, Float> getExpensePerItemTable() {
		if (this.expensePerItemTable != null)
			return this.expensePerItemTable;
		this.expensePerItemTable = this.streamOperationsHandler.expensePerItem();
		return this.expensePerItemTable;
	}

	public KTable<Long, Float> getRevenuePerItemTable() {
		if (this.revenuePerItemTable != null)
			return this.revenuePerItemTable;
		this.revenuePerItemTable = this.streamOperationsHandler.revenuePerItem();
		return this.revenuePerItemTable;
	}

	public KTable<Long, Float> getTotalExpenseTable() {
		if (this.totalExpenseTable != null)
			return this.totalExpenseTable;
		this.totalExpenseTable = this.streamOperationsHandler.totalExpense();
		return this.totalExpenseTable;
	}

	public KTable<Long, Float> getTotalRevenueTable() {
		if (this.totalRevenueTable != null)
			return this.totalRevenueTable;
		this.totalRevenueTable = this.streamOperationsHandler.totalRevenue();
		return this.totalRevenueTable;
	}

	public KTable<Long, Float> getTotalProfitTable() {
		if (this.totalProfitTable != null) {
			return this.totalProfitTable;
		}
		this.totalProfitTable = this.streamOperationsHandler.totalProfit(
				this.getTotalRevenueTable(),
				this.getTotalExpenseTable());
		return this.totalProfitTable;
	}

	public KTable<Long, Float> getAverageExpensePerItemTable() {
		if (this.averageExpensePerItemTable != null)
			return this.averageExpensePerItemTable;
		this.averageExpensePerItemTable = this.streamOperationsHandler.averageExpensePerItem(getExpensePerItemTable());
		return this.averageExpensePerItemTable;
	}

	public KTable<Long, Float> getAverageExpensePerOrderTable() {
		if (this.averageExpensePerOrderTable != null)
			return this.averageExpensePerOrderTable;
		this.averageExpensePerOrderTable = this.streamOperationsHandler.averageExpensePerOrder(getTotalExpenseTable());
		return this.averageExpensePerOrderTable;
	}

	public KTable<Long, Float> getProfitPerItemTable() {
		if (this.profitPerItemTable != null)
			return this.profitPerItemTable;
		this.profitPerItemTable = this.streamOperationsHandler.profitPerItem(
				getRevenuePerItemTable(),
				getExpensePerItemTable()
		);
		return this.profitPerItemTable;
	}

	public KStream<Windowed<Long>, Float> getTotalRevenueLastHourStream() {
		if (this.totalRevenueLastHourStream != null)
			return this.totalRevenueLastHourStream;
		this.totalRevenueLastHourStream = this.streamOperationsHandler.totalRevenueLastHour();
		return this.totalRevenueLastHourStream;
	}

	public KStream<Windowed<Long>, Float> getTotalExpenseLastHourStream() {
		if (this.totalExpenseLastHourStream != null)
			return this.totalExpenseLastHourStream;
		this.totalExpenseLastHourStream = this.streamOperationsHandler.totalExpenseLastHour();
		return this.totalExpenseLastHourStream;
	}

	public KTable<Long, Float> getTotalProfitLastHourTable() {
		if (totalProfitLastHourTable !=  null)
			return this.totalProfitLastHourTable;
		this.totalProfitLastHourTable = this.streamOperationsHandler.totalProfitLastHour(
				this.getTotalRevenueLastHourStream(),
				this.getTotalExpenseLastHourStream()
		);
		return this.totalProfitLastHourTable;
	}

	public KTable<Long, String> getCountryHighestSalesTable() {
		if(this.countryHighestSalesTable != null) {
			return this.countryHighestSalesTable;
		}
		this.countryHighestSalesTable = this.streamOperationsHandler.countryHighestSales();
		return this.countryHighestSalesTable;
	}

}
