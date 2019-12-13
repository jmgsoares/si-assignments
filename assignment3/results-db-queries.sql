select i.name, e.value from "Results"."ExpensePerItem" e
    inner join items i
        on cast(e.id as bigint)= i.id;

select i.name, a.value from "Results"."AverageExpenseByItem" a
    inner join items i
        on cast(a.id as bigint)=i.id;

select a.value from "Results"."AverageExpenseByOrder" a;

select c.name, i.name, a.value2 from "Results"."CountryHighestSales" a
inner join items i on cast(a.id as bigint)=i.id
inner join countries c on cast(a.value as bigint)=c.id;