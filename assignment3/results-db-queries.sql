-- R1.1 Insert country
insert into countries (name) values ('USA');

-- R1.2 Update country
update countries
set name=('Baguette')
where name=('France');

-- R2 List countries
select name from countries order by name;

-- R3.1 Insert item
insert into items (name) values ('Key');

-- R3.2 Update item
update items
set name=('KeyFob')
where name=('Key');

-- R4 List items
select name from items order by name;

-- R5 Revenue per item
select i.name as item, r.value as value from "Results"."RevenuePerItem" r
inner join items i
on r.id=i.id
order by value desc;

-- R6 Expense per item
select i.name as item, r.value as value from "Results"."ExpensePerItem" r
inner join items i
on r.id = i.id
order by value desc;

-- R7 Profit per item
select i.name as item, r.value as value from "Results"."ProfitPerItem" r
inner join items i
on r.id = i.id
order by value desc;

-- R8 Total revenue
select value from "Results"."TotalRevenue";

-- R9 Total Expense
select value from "Results"."TotalExpense";

-- R10 Total profit
select value from "Results"."TotalProfit";

-- R11 Average expense per order (by item)
select i.name as item, r.value as value from "Results"."AverageExpenseByItem" r
inner join items i
on r.id=i.id
order by value desc;

-- R12 Average expense per order
select value from "Results"."AverageExpenseByOrder";

-- R13 Item with the highest profit
select i.name as name, r.value2 as value from "Results"."MostProfitableItem" r
inner join items i
on r.value = i.id;

-- R14 Total revenue in the last hour
select value from "Results"."TotalRevenueLastHour";

-- R15 Total expense in the last hour
select value from "Results"."TotalExpenseLastHour";

-- R16 Total profit in the last hour
select value from "Results"."TotalProfitLastHour";

-- R17 Country of highest sales by item
select i.name as item, c.name as country, r.value2 as value from "Results"."CountryHighestSales" r
inner join items i on r.id = i.id
inner join countries c on r.value = c.id
order by value2 desc;
