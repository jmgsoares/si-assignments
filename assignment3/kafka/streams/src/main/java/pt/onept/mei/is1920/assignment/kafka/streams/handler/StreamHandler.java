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
	private KTable<Long, Float> averageExpensePerOrderStream = null;
	private KTable<Long, Float> totalProfitStream = null;

	private KStream<Windowed<Long>, Float> totalRevenueLastHourTable = null;
	private KStream<Windowed<Long>, Float> totalExpenseLastHourTable = null;
	private KStream<Windowed<Long>, Float> totalProfitLastHourTable = null;

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
		if (this.totalProfitStream != null) {
			return this.totalProfitStream;
		}
		this.totalProfitStream = this.streamOperationsHandler.totalProfit(
				this.getTotalRevenueTable(),
				this.getTotalExpenseTable());
		return this.totalProfitStream;
	}

	public KTable<Long, Float> getAverageExpensePerItemTable() {
		if (this.averageExpensePerItemTable != null)
			return this.averageExpensePerItemTable;
		this.averageExpensePerItemTable = this.streamOperationsHandler.averageExpensePerItem(getExpensePerItemTable());
		return this.averageExpensePerItemTable;
	}

	public KTable<Long, Float> getAverageExpensePerOrderTable() {
		if (this.averageExpensePerOrderStream != null)
			return this.averageExpensePerOrderStream;
		this.averageExpensePerOrderStream = this.streamOperationsHandler.averageExpensePerOrder(getTotalExpenseTable());
		return this.averageExpensePerOrderStream;
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

	public KStream<Windowed<Long>, Float> getTotalRevenueLastHourTable() {
		if (this.totalRevenueLastHourTable != null)
			return this.totalRevenueLastHourTable;
		this.totalRevenueLastHourTable = this.streamOperationsHandler.totalRevenueLastHour();
		return this.totalRevenueLastHourTable;
	}

	public KStream<Windowed<Long>, Float> getTotalExpenseLastHourTable() {
		if (this.totalExpenseLastHourTable != null)
			return this.totalExpenseLastHourTable;
		this.totalExpenseLastHourTable = this.streamOperationsHandler.totalExpenseLastHour();
		return this.totalExpenseLastHourTable;
	}

	public KStream<Windowed<Long>, Float> getTotalProfitLastHourTable() {
		if (totalProfitLastHourTable !=  null)
			return this.totalProfitLastHourTable;
		this.totalProfitLastHourTable = this.streamOperationsHandler.totalProfitLastHour(
				this.getTotalRevenueLastHourTable(),
				this.getTotalExpenseLastHourTable()
		);
		return this.totalProfitLastHourTable;
	}

}
