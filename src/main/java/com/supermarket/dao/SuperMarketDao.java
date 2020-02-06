package com.supermarket.dao;


import com.supermarket.bean.*;
import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.OrderItemVO;

import java.util.List;

public class SuperMarketDao extends BaseDao {
    public User getUser(String username, String password) {
        String sql = "select * from tb_user where username = ? and password = ?";
        Object[] paramValue = {username, password};
        List<User> users = super.query(sql, paramValue, User.class);

        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public Commodity getCommodity(int commodityId) {
        String sql = "select * from tb_commodity where id = ?";
        Object[] paramValue = {commodityId};
        List<Commodity> commodities = super.query(sql, paramValue, Commodity.class);

        if (commodities.size() > 0) {
            return commodities.get(0);
        }
        return null;
    }
    public List<Commodity> getCommodities() {
        String sql = "select * from tb_commodity";
        Object[] paramValue = {};
        List<Commodity> commodities = super.query(sql, paramValue, Commodity.class);
        return commodities;
    }
    public void insertCommodity(Commodity commodity) {
        String select_sql = "select * from tb_commodity where id = ?";
        String insert_sql = "insert tb_commodity values(?,?,?,?,?,?)";

        int id = commodity.getId();
        double price = commodity.getPrice();
        String name = commodity.getName();
        String specification = commodity.getSpecification();
        String units = commodity.getUnits();
        int stock = commodity.getStock();

        Object[] select_paramValue = {commodity.getId()};
        List<Commodity> commodities = super.query(select_sql, select_paramValue, Commodity.class);

        if (commodities != null && commodities.size() > 0) {
            Commodity oldCommodity = commodities.get(0);
            stock = stock + oldCommodity.getStock();
        }
        Object[] insert_paramValue = {id, name, specification, units, price, stock};

        super.update(insert_sql, insert_paramValue);
    }
    public void addCommodity(Integer shoppingNum, CommodityVO commodity) {
        String insert_sql = "insert into tb_commodity values(?,?,?,?,?,?,？)";

        int id = commodity.getId();
        double price = commodity.getPrice();
        String name = commodity.getName();
        String specification = commodity.getSpecification();
        String units = commodity.getUnits();
        int stock = commodity.getStock();
        Object[] insert_paramValue = {id, name, specification, units, price, stock,0};

        super.update(insert_sql, insert_paramValue);
    }


    public Member getMember(int memberId) {
        String sql = "select * from tb_member where id = ?";
        Object[] paramValue = {memberId};
        List<Member> members = super.query(sql, paramValue, Member.class);
        if (members.size() > 0) {
            return members.get(0);
        }
        return null;
    }

    public List<Member> getMembers() {
        String sql = "select * from tb_member";
        Object[] paramValue = {};
        List<Member> members = super.query(sql, paramValue, Member.class);
        return members;
    }
    public void updateMember(Member member) {
        String update_sql = "update tb_member set points=?,total=? where id=?";
        int id = member.getId();
        int points = member.getPoints();
        double total = member.getTotal();
        Object[] update_paramValue = { points, total,id};
        super.update(update_sql, update_paramValue);
    }
    public void addMember(Member member) {
        String insert_sql = "insert into tb_Member values(?,?,?,?,?,?,?)";
        int id = member.getId();
        String name = member.getName();
        int points = member.getPoints();
        double total = member.getTotal();
        String phone = member.getPhone();
        int registerTime = member.getRegisterTime();
        int updateTime = member.getUpdateTime();
        Object[] insert_paramValue = {id, name, phone, points, total, registerTime, updateTime};
        super.update(insert_sql, insert_paramValue);
    }

    public Order getOrder(int orderId) {
        String sql = "select * from tb_order where order_number =?";
        Object[] paramValue = {orderId};
        List<Order> orderList = super.query(sql,paramValue,Order.class);
        if (orderList.size()>0) {
            Order order = orderList.get(0);
            return order;
        }
        return  null;
    }

    public void updateOrder(Order order) {
        String update_sql = "update tb_order set sum=?,userId=?,memberId=?,checkoutType=? where orderNumber =?";
        int orderNumber = order.getOrderNumber();
        double sum = order.getSum();
        int userId = order.getUserId();
        int memberId = order.getMemberId();
        int checkoutType = order.getCheckoutType();
        Object[] update_paramValue = {sum,userId,memberId,checkoutType,orderNumber};
        super.update(update_sql, update_paramValue);
    }

    public void addOrder(Order order) {
        String insert_sql = "insert into tb_order values(?,?,?,?,?,?,?)";
        int id = order.getId();
        int orderNumber = order.getOrderNumber();
        int memberId = order.getMemberId();
        double sum = order.getSum();
        int userId = order.getUserId();
        int checkoutType = order.getCheckoutType();
        int checkoutTime = order.getCheckoutTime();
        Object[] insert_paramValue = {id, orderNumber, sum, userId, memberId, checkoutType, checkoutTime};
        super.update(insert_sql, insert_paramValue);
    }

    public OrderItem getSameOrder(int shoppingNumber, int commodityID) {
        String sql = "select * from tb_order_item where orderNumber =? and commodityId=?";
        Object[] paramValue = {shoppingNumber,commodityID};
        List<OrderItem> list = super.query(sql, paramValue, OrderItem.class);
        if (list.size()>0){
            OrderItem orderItem = list.get(0);
            return orderItem;
        }
        return null;
    }

    public void updateOrderCount(OrderItem orderItem) {
        String update_sql = "update tb_order_item set count=?,total=? where orderNumber=? and commodityId=? and " +
                "isChecked=?";
        int orderNumber = orderItem.getOrderNumber();
        int commodityId = orderItem.getCommodityId();
        int count = orderItem.getCount();
        double total = orderItem.getTotal();
        Object[] update_paramValue = {count,total,orderNumber,commodityId,0};
        super.update(update_sql, update_paramValue);
    }
    public List<OrderItem> getOrderItems(int orderId) {

        String sql = "select * from " +
                "tb_order_item where " +
                "orderNumber =?" ;
        Object[] paramValue = {orderId};
        List<OrderItem> list = super.query(sql,paramValue,OrderItem.class);
        return list;
    }
    public List<OrderItem> getOrderItemCheck(int orderId,int isChecked) {

        String sql = "select * from tb_order_item where orderNumber =? and isChecked=?" ;
        Object[] paramValue = {orderId,isChecked};
        List<OrderItem> list = super.query(sql,paramValue,OrderItem.class);
        return list;
    }
    public void updateOrderItem(OrderItem orderItem) {
        String update_sql = "update tb_order_item set isChecked=? where orderNumber=? and commodityId=?";
        int orderNumber = orderItem.getOrderNumber();
        int commodityId = orderItem.getCommodityId();
        int isChecked = orderItem.getIsChecked();
        Object[] update_paramValue = {isChecked,orderNumber,commodityId};
        super.update(update_sql, update_paramValue);
    }

    public void addRecord(MemberRecord memRecord) {
        String insert_sql = "insert into tb_member_record (memberId,userId,orderNumber, sum, balance,receivedPoints, checkoutTime) values(?,?,?,?,?,?,?)";
        int id = memRecord.getId();
        int orderNumber = memRecord.getOrderNumber();
        int memberId = memRecord.getMemberId();
        double sum = memRecord.getSum();
        double balance = memRecord.getBalance();
        int userId = memRecord.getUserId();
        int receivedPoints = memRecord.getReceivedPoints();
        int checkoutTime = memRecord.getCheckoutTime();
        Object[] insert_paramValue = { memberId,userId,orderNumber, sum, balance,receivedPoints, checkoutTime};
        super.update(insert_sql, insert_paramValue);
    }

    public void updateCommodityChecked(Commodity commodity) {
        String sql="update tb_commodity set stock=? where id=?";
        int id = commodity.getId();
        int stock = commodity.getStock();
        Object[] update_paramValue = {stock,id};
        super.update(sql,update_paramValue);
    }

    public void addOrderItem(OrderItem orderItem) {
        String insert_sql = "insert into tb_order_item (orderNumber,commodityId,commodityName,price,count,total," +
                "isChecked)values(?,?,?,?,?,?,?)";
        int orderNumber = orderItem.getOrderNumber();
        String commodityName = orderItem.getCommodityName();
        int commodityId = orderItem.getCommodityId();
        double price = orderItem.getPrice();
        int count = orderItem.getCount();
        double total = orderItem.getTotal();
        int isChecked = orderItem.getIsChecked();
        Object[] update_paramValue = {orderNumber,commodityId,commodityName,price,count,total,isChecked};
        super.update(insert_sql, update_paramValue);
    }



    public List<OrderItemVO> getAllUncheck(int shoppingNumber) {
        String sql ="SELECT" +
                " ord.commodityId ," +
                " ord.commodityName ," +
                " ord.count," +
                " ord.orderNumber ," +
                " ord.price," +
                " ord.total," +
                " comm.specification," +
                "  comm.units," +
                " comm.stock " +
                " FROM" +
                " tb_order_item AS ord " +
                " LEFT JOIN tb_commodity AS comm ON ord.commodityId = comm.id " +
                " WHERE " +
                " orderNumber = ? and isChecked = 0";
        Object[] paramValue ={shoppingNumber};
        List<OrderItemVO> list = query(sql, paramValue, OrderItemVO.class);
        return list;
    }

    public List<OrderItemVO> getAllChecked(int shoppingNumber) {
        String sql ="SELECT" +
                " ord.commodityId ," +
                " ord.commodityName ," +
                " ord.count," +
                " ord.orderNumber ," +
                " ord.price," +
                " ord.total," +
                " comm.specification," +
                "  comm.units," +
                " comm.stock " +
                " FROM" +
                " tb_order_item  ord " +
                " LEFT JOIN tb_commodity AS comm ON ord.commodityId = comm.id " +
                " WHERE " +
                " orderNumber = ? and isChecked = 1";
        Object[] paramValue ={shoppingNumber};
        List<OrderItemVO> list = query(sql, paramValue, OrderItemVO.class);
        return list;
    }


    public void inputCommodity(Commodity commodity) {
        String insert_sql = "insert into tb_commodity values(?,?,?,?,?,?)";

        int id = commodity.getId();
        double price = commodity.getPrice();
        String name = commodity.getName();
        String specification = commodity.getSpecification();
        String units = commodity.getUnits();
        int stock = commodity.getStock();
        Object[] insert_paramValue = {id, name, specification, units, price, stock};

        super.update(insert_sql, insert_paramValue);
    }
}
