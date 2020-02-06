package com.supermarket.service;


import com.alibaba.druid.util.StringUtils;
import com.supermarket.bean.*;
import com.supermarket.dao.SuperMarketDao;
import com.supermarket.pojo.CommodityVO;
import com.supermarket.pojo.IDUtil;
import com.supermarket.pojo.OrderItemVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupermarketService {
    private SuperMarketDao superMarketDao;

    public SupermarketService() {
        this.superMarketDao = new SuperMarketDao();
    }


    /**
     * 查询管理员信息
     *
     * @param username
     * @param password
     * @return
     */
    public User getUser(String username, String password) {
        return superMarketDao.getUser(username, password);
    }

    /**
     * 删除商品库存
     *
     * @param commodityId
     * @return
     */
//    public void deleteCommodity(int commodityId) {
//
//        superMarketDao.deleteCommodity(commodityId);
//    }


    /**
     * 查询单个商品
     *
     * @param commodityId
     * @return
     */
    public Commodity getCommodity(int commodityId) {
        return superMarketDao.getCommodity(commodityId);
    }

    /**
     * 查询所有商品
     *
     * @return
     */
    public List<Commodity> getCommodities() {
        return superMarketDao.getCommodities();
    }


    /**
     * 查询会员
     *
     * @param id
     * @return
     */
    public Member getMember(int id) {

        return superMarketDao.getMember(id);
    }

    public List<Member> getMembers() {
        return superMarketDao.getMembers();
    }

    /**
     * 添加会员
     *
     * @param member
     */
    public void addMember(Member member) {
        superMarketDao.addMember(member);
    }

    /**
     * 更新会员
     *
     * @param member
     */
    public void updateMember(Member member) {
        superMarketDao.updateMember(member);
    }



    /**
     * 添加订单
     *
     * @param shoppingNumber
     */
    public void addOrder(int shoppingNumber) {
        Order order = new Order();
        order.setOrderNumber(shoppingNumber);
        order.setCheckoutType(0);
        superMarketDao.addOrder(order);
    }

    /**
     * 查询未结账的所有记录
     *
     * @param shoppingNumber
     * @return
     */
    public List<OrderItemVO> getAllUncheck(int shoppingNumber) {
        return superMarketDao.getAllUncheck(shoppingNumber);
    }


    /**
     * 查询已结账的所有记录
     *
     * @param shoppingNumber
     * @return
     */
    public List<OrderItemVO> getAllChecked(int shoppingNumber) {
        return superMarketDao.getAllChecked(shoppingNumber);
    }

    /**
     * 更新订单表
     *
     * @param orderNum
     */
    public void updateOrder(int orderNum, double totalPrice, int memberId) {
        Order order = new Order();
        order.setOrderNumber(orderNum);
        order.setSum(totalPrice);
        order.setMemberId(memberId);
        order.setCheckoutType(1);
        superMarketDao.updateOrder(order);

    }

    /**
     * 查询订单详情信息
     *
     * @return
     */
    public List<OrderItem> getOrderItems(int orderNum) {
        return superMarketDao.getOrderItems(orderNum);
    }

    /**
     * 添加订单详情
     *
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        superMarketDao.addOrderItem(orderItem);
    }

    /**
     * 更新订单详情结账状态
     *
     * @param orderNumber
     * @param isChecked
     */
    public void updateOrderItem(int orderNumber, int commodityId, int isChecked) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNumber(orderNumber);
        orderItem.setCommodityId(commodityId);
        orderItem.setIsChecked(isChecked);
        superMarketDao.updateOrderItem(orderItem);
    }

    /**
     * 更新订单详情商品数量
     *
     * @param orderNumber
     * @param count
     */
    public void updateOrderCount(int orderNumber, int commodityId, int count, double totalPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNumber(orderNumber);
        orderItem.setCommodityId(commodityId);
        orderItem.setCount(count);
        orderItem.setTotal(totalPrice);
        superMarketDao.updateOrderCount(orderItem);
    }

    /**
     * 更新库存数量
     *
     * @param commodityID
     */
    public void updateCommodityChecked(int commodityID, int newStock) {
        Commodity commodity = new Commodity();
        commodity.setId(commodityID);
        commodity.setStock(newStock);
        superMarketDao.updateCommodityChecked(commodity);
    }

    public void addMemberRecord(int memberID, int shopNum, double totalCost) {
        int total = new Double(totalCost).intValue();
        MemberRecord memRecord = new MemberRecord();
        memRecord.setMemberId(memberID);
        memRecord.setOrderNumber(shopNum);
        memRecord.setReceivedPoints(total);
        memRecord.setSum(totalCost);
        superMarketDao.addRecord(memRecord);
    }

    /**
     * 现金结账流程，添加事务
     *
     * @param orderNumber
     * @param total
     * @return
     */

    public int checkoutByCash(String orderNumber, String total) {
        int orderNum = Integer.parseInt(orderNumber);
        double totalCost = Double.parseDouble(total);
        //更新订单信息
        updateOrder(orderNum, totalCost, 0);
        //更新库存数量commdity表
        List<OrderItem> orderItemList = getOrderItems(orderNum);
        for (OrderItem item : orderItemList) {
            int commodityID = item.getCommodityId();
            Commodity commodity = getCommodity(commodityID);
            int stock = commodity.getStock();
            int count = item.getCount();
            int newStock = stock - count;
            if (newStock < 0) {
                newStock = 0;
            }
            updateCommodityChecked(commodityID, newStock);
            //更新订单详情状态为已结账
            int isCheck = 1;
            updateOrderItem(orderNum, commodityID, isCheck);
        }
        return 0;
    }

    /**
     * 会员结账流程，添加事务
     *
     * @param shopNumber
     * @param total
     * @return
     */

    public Map<String, Object> checkoutByMember(String shopNumber, String total, String memberId) {
        Map<String, Object> map = new HashMap();
        if ("0".equals(shopNumber.trim()) || StringUtils.isEmpty(total) || "0".equals(memberId.trim())) {
            map.put("error", 99999999);
            return map;
        }
        int shopNum = Integer.parseInt(shopNumber);
        int memberID = Integer.parseInt(memberId);
        double totalCost = Double.parseDouble(total);
        int totalMember = new Double(totalCost).intValue();
        //更新订单信息
        updateOrder(shopNum, totalCost, memberID);
        //更新库存数量commdity表
        List<OrderItem> orderItemList = getOrderItems(shopNum);
        for (OrderItem item : orderItemList) {
            int commodityID = item.getCommodityId();
            Commodity commodity = getCommodity(commodityID);
            int stock = commodity.getStock();
            int count = item.getCount();
            int newStock = stock - count;
            if (newStock < 0) {
                newStock = 0;
            }
            updateCommodityChecked(commodityID, newStock);
            //更新订单详情状态为已结账
            int isCheck = 1;
            updateOrderItem(shopNum, commodityID, isCheck);
        }
        //添加会员消费记录
        addMemberRecord(memberID, shopNum, totalCost);
        //更新会员记录
        //查会员记录表
        Member member = getMember(memberID);
        int points = member.getPoints();
        int pointMem = points + totalMember;
        double totalMem = member.getTotal();
        double newTotal = totalMem - totalCost;
        if (newTotal < 0) {
            newTotal = 0.00;
        }
        Member m = new Member();
        m.setId(memberID);
        m.setPoints(pointMem);
        m.setTotal(newTotal);
        updateMember(m);
        map.put("points", pointMem);
        map.put("total", newTotal);
        map.put("error", 00000000);
        return map;

    }

    /**
     * 添加商品流程，添加事务
     *
     * @param commodityID
     * @param count
     * @param shoppingNumStr
     * @return
     */

    public int addCommodity(String commodityID, String count, String shoppingNumStr) {
        int commodityCount = Integer.parseInt(count);
        int commodityId = Integer.parseInt(commodityID);
        //根据Id查商品详情
        Commodity commodity = getCommodity(Integer.parseInt(commodityID));
        if (commodity == null || commodity.getStock() <= 0) {
            return 1;
        }
        if (commodity.getStock() < commodityCount) {
            commodityCount = commodity.getStock();
        }
        //根据流水号判断是否添加订单
        int shoppingNumber = Integer.parseInt(shoppingNumStr);
        if (shoppingNumber == 0) {
            //初次购买，添加订单记录
            shoppingNumber = IDUtil.getId();
            addOrder(shoppingNumber);
        }
        //查询是否存在相同的订单商品
        OrderItem sameOrder = getSameOrder(commodityId, shoppingNumber);
        //没有相同订单，添加记录
        if (sameOrder == null) {
            //转换实体CommodityOV，
            CommodityVO commodityVO = new CommodityVO();
            commodityVO.setPrice(commodity.getPrice());
            commodityVO.setCount(commodityCount);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNumber(shoppingNumber);
            orderItem.setCommodityId(commodityId);
            orderItem.setCommodityName(commodity.getName());
            orderItem.setPrice(commodity.getPrice());
            orderItem.setCount(commodityCount);
            orderItem.setTotal(commodityVO.getTotalPrice());
            orderItem.setIsChecked(0);
            //添加订单详情
            addOrderItem(orderItem);
            return shoppingNumber;
        }
        //存在相同订单，更新记录
        commodityCount += sameOrder.getCount();
        if (commodity.getStock() < commodityCount) {
            commodityCount = commodity.getStock();
        }
        //转换实体CommodityOV，
        double totalPrice = commodityCount * commodity.getPrice();
        //更新订单详情
        updateOrderCount(shoppingNumber, commodityId, commodityCount, totalPrice);
        return shoppingNumber;
    }

    public OrderItem getSameOrder(int commodityID, int shoppingNumber) {
        return superMarketDao.getSameOrder(shoppingNumber, commodityID);
    }

    public void inputCommodity(Commodity commodity) {
        superMarketDao.inputCommodity(commodity);
    }
}
