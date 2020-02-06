package com.supermarket.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.supermarket.bean.Commodity;
import com.supermarket.bean.Member;
import com.supermarket.bean.OrderItem;
import com.supermarket.bean.User;
import com.supermarket.pojo.OrderItemVO;
import com.supermarket.service.SupermarketService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PortalServlet extends HttpServlet {

    private String cashierPage = "/page/cashier.jsp";
    private String managerPage = "/page/manager.jsp";
    private String commodityPage = "/page/commodity.jsp";
    private String receiptPage = "/page/receipt.jsp";
    private String loginPage = "/page/login.jsp";
    private String errorPage = "/page/error.jsp";
    private SupermarketService supermarketService;
    String forwardPage = errorPage;

    @Override
    public void init() throws ServletException {
        supermarketService = new SupermarketService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUri = req.getRequestURI();
        System.out.println(currentUri + " this request method is GET method");

        if ("/supermarket/login".equals(currentUri)) {
            login(req, resp);
        }
        if ("/supermarket/addBoughtCommodity".equals(currentUri)) {
            addBoughtCommodity(req, resp);
        }
        if ("/supermarket/back2cashier".equals(currentUri)) {
            back2cashier(req, resp);
        }
        if ("/supermarket/removeBoughtCommodity".equals(currentUri)) {
            removeBoughtCommodity(req, resp);
        }
        if ("/supermarket/checkoutByCash".equals(currentUri)) {
            checkoutByCash(req, resp);
        }
        if ("/supermarket/checkoutByMember".equals(currentUri)) {
            checkoutByMember(req, resp);
        }
        if ("/supermarket/getCommodities".equals(currentUri)) {
            getCommodities(req, resp);
        }
        if ("/supermarket/getMember".equals(currentUri)) {
            getMember(req, resp);
        }
        if ("/supermarket/inputCommodities".equals(currentUri)) {
            inputCommodity(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUri = req.getRequestURI();
        System.out.println(currentUri + " this request method is POST method");

        if ("/supermarket/login".equals(currentUri)) {
            login(req, resp);
        }
        if ("/supermarket/addCommodity".equals(currentUri)) {
            addBoughtCommodity(req, resp);
        }
        if ("/supermarket/back2cashier".equals(currentUri)) {
            back2cashier(req, resp);
        }
        if ("/supermarket/removeBoughtCommodity".equals(currentUri)) {
            removeBoughtCommodity(req, resp);
        }
        if ("/supermarket/checkoutByCash".equals(currentUri)) {
            checkoutByCash(req, resp);
        }
        if ("/supermarket/checkoutByMember".equals(currentUri)) {
            checkoutByMember(req, resp);
        }
        if ("/supermarket/getCommodities".equals(currentUri)) {
            getCommodities(req, resp);
        }
        if ("/supermarket/getMember".equals(currentUri)) {
            getMember(req, resp);
        }
        if ("/supermarket/inputCommodities".equals(currentUri)) {
            inputCommodity(req, resp);
        }
        if ("/supermarket/addMember".equals(currentUri)) {
            addMember(req, resp);
        }
    }

    /**
     * 登录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardPage = errorPage;
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String username = req.getParameter("username");
        if (StringUtils.isEmpty(username)) {
            req.setAttribute("message", "username cannot be empty");
            view.forward(req, resp);
            return;
        }
        String password = req.getParameter("password");
        if (StringUtils.isEmpty(password)) {
            req.setAttribute("message", "password cannot be empty");
            view.forward(req, resp);
            return;
        }
        String role = req.getParameter("role");
        if (StringUtils.isEmpty(role)) {
            req.setAttribute("message", "role cannot be empty");
            view.forward(req, resp);
            return;
        }
        User user = supermarketService.getUser(username, password);
        if (user == null) {
            req.setAttribute("message", "The user is not exist");
            view.forward(req, resp);
            return;
        }
        if (user.getRole() == 1 && user.getRole() == Integer.parseInt(role)) {
            forwardPage = managerPage;
            RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
            List<Member> list = supermarketService.getMembers();
            req.setAttribute("members", list);
            view1.forward(req, resp);
            return;
        } else if (user.getRole() == 2 && user.getRole() == Integer.parseInt(role)) {
            forwardPage = cashierPage;
            RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
            req.setAttribute("shoppingNum", 0);
            view1.forward(req, resp);
            return;
        } else if (user.getRole() == 1 && 2 == Integer.parseInt(role)) {
            forwardPage = cashierPage;
            RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
            req.setAttribute("shoppingNum", 0);
            view1.forward(req, resp);
            return;
        }

    }

    private void addMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String id = req.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            req.setAttribute("message", "id can not be empty ");
            view.forward(req, resp);
            return;
        }
        Member mem = supermarketService.getMember(Integer.parseInt(id));
        if (mem != null) {
            req.setAttribute("message", "The id exists");
            view.forward(req, resp);
            return;
        }
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String total = req.getParameter("total");
        Member member = new Member();
        member.setId(Integer.parseInt(id));
        member.setName(name);
        member.setPhone(phone);
        if (StringUtils.isEmpty(total)) {
            total = "0.00";
        }
        member.setPoints(0);
        member.setTotal(Double.parseDouble(total));
        member.setRegisterTime(0);
        member.setUpdateTime(0);
        supermarketService.addMember(member);
        List<Member> list = supermarketService.getMembers();
        req.setAttribute("members", list);
        String forwardPage = managerPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);
    }

    private void getMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * memberID: 22
         * shopNum: 115160118
         */
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String memberID = req.getParameter("memberID");
        if ("".equals(memberID)) {
            req.setAttribute("message", "id can not be empty ");
            view.forward(req, resp);
            return;
        }

        Member mem = supermarketService.getMember(Integer.parseInt(memberID));
        String member = JSON.toJSONString(mem);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.append(member);

    }

    /**
     * 返回收银页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void back2cashier(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("shoppingNum", 0);
        String forwardPage = cashierPage;
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        view.forward(req, resp);
    }

    /**
     * 添加购买商品
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addBoughtCommodity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String commodityID = req.getParameter("commodityID");
        String count = req.getParameter("count");
        String shoppingNumStr = req.getParameter("shoppingNum").trim();
        if (StringUtils.isEmpty(commodityID) || StringUtils.isEmpty(count) || StringUtils.isEmpty(shoppingNumStr)) {
            req.setAttribute("message", "Empty parameter exists");
            view.forward(req, resp);
            return;
        }
        int shopNumber = supermarketService.addCommodity(commodityID, count, shoppingNumStr);
        if (shopNumber == 0) {
            req.setAttribute("message", "The commodity does not exist or the stock is 0");
            view.forward(req, resp);
            return;
        }

        Double totalCost = 0.0;
        int category = 0;
        //回显页面,先查该订单号的所有信息，
        //先查订单详情，然后遍历commodityId查商品，组合OrderItemVO，返回
        List<OrderItem> orderItems = supermarketService.getOrderItems(shopNumber);
        OrderItemVO orderItemVO = new OrderItemVO();
        for (OrderItem item :orderItems){
            int id = item.getCommodityId();
            Commodity commodity = supermarketService.getCommodity(id);
            orderItemVO.setCommodityId(id);
            orderItemVO.setCommodityName(commodity.getName());
            orderItemVO.setCount(item.getCount());
            orderItemVO.setPrice(item.getPrice());
            orderItemVO.setSpecification(commodity.getSpecification());
            orderItemVO.setStock(commodity.getStock());
            orderItemVO.setUnits(commodity.getUnits());
            orderItemVO.setTotal(item.getTotal());
            totalCost += item.getTotal();
        }

        List<OrderItemVO> ord = new ArrayList<OrderItemVO>();
        ord.add(orderItemVO);
        category = ord.size();
        req.setAttribute("shoppingNum", shopNumber);
        req.setAttribute("commodityList", ord);
        req.setAttribute("totalCost", totalCost);
        req.setAttribute("category", category);
        String forwardPage = cashierPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);

    }

    private void removeBoughtCommodity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String commodityId = req.getParameter("commodityID").trim();
        String shoppingNumStr = req.getParameter("shoppingNum").trim();

        if (StringUtils.isEmpty(commodityId) || StringUtils.isEmpty(shoppingNumStr)) {
            req.setAttribute("message", "Empty parameter exists");
            view.forward(req, resp);
            return;
        }
        int ischeck = 2;
        supermarketService.updateOrderItem(Integer.parseInt(shoppingNumStr), Integer.parseInt(commodityId), ischeck);
        double totalCost = 0.00;
        //回显页面
        //先查该订单号的所有信息，
        List<OrderItemVO> ord = supermarketService.getAllUncheck(Integer.parseInt(shoppingNumStr));
        for (OrderItemVO item1 : ord) {
            totalCost += item1.getTotal();
        }
        int category = ord.size();
        req.setAttribute("shoppingNum", shoppingNumStr);
        req.setAttribute("orderItemList", ord);
        req.setAttribute("totalCost", totalCost);
        req.setAttribute("category", category);
        String forwardPage = cashierPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);
    }

    private void getCommodities(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String forwardPage = errorPage;
        User user = supermarketService.getUser(username, password);

        if (user != null) {
            forwardPage = commodityPage;

        }

        List<Commodity> commodities = supermarketService.getCommodities();
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        req.setAttribute("commodities", commodities);
        view.forward(req, resp);
    }

    private void inputCommodity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
//        Integer flag = new Integer(Integer.parseInt(req.getParameter("flag")));
//        HttpSession session = req.getSession();
//        if (!flag.equals(session.getAttribute("flag"))) {
//            List<Commodity> commodityList = supermarketService.getCommodities();
//            req.setAttribute("commodities", commodityList);
//            view.forward(req, resp);
//            return;
//        }
        String id = req.getParameter("commodityId");
        String price = req.getParameter("price");
        String name = req.getParameter("name");
        String units = req.getParameter("units");
        String sp = req.getParameter("specification");
        String stock = req.getParameter("stock");
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(price) || StringUtils.isEmpty(name) || StringUtils.isEmpty(sp) || StringUtils.isEmpty(stock) || StringUtils.isEmpty(units)) {
            req.setAttribute("message", "Empty parameter exists");
            view.forward(req, resp);
            return;
        }
        //判断商品是否已经存在
        Commodity comm = supermarketService.getCommodity(Integer.parseInt(id));
        if (comm != null) {
            req.setAttribute("message", "The commodity is exists");
            view.forward(req, resp);
            return;
        }
        Commodity commodity = new Commodity();
        commodity.setId(Integer.parseInt(id));
        commodity.setPrice(Double.parseDouble(price));
        commodity.setName(name);
        commodity.setUnits(units);
        commodity.setSpecification(sp);
        commodity.setStock(Integer.parseInt(stock));
        supermarketService.inputCommodity(commodity);
        List<Commodity> commodityList = supermarketService.getCommodities();
        req.setAttribute("commodities", commodityList);
//        session.removeAttribute("flag");
        forwardPage = commodityPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);
    }

    private void checkoutByCash(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * shoppingNum: 1132301181
         * commodityID:
         * count:
         * category: 2
         * total_cost: 10.0
         * cash_receive: 20
         * cash_balance: 10
         * memberID:
         */
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String cashReceive = req.getParameter("cash_receive");
        String cashBalance = req.getParameter("cash_balance");
        String shopNumber = req.getParameter("shoppingNum");
        String total = req.getParameter("total_cost");
        if (StringUtils.isEmpty(shopNumber) || StringUtils.isEmpty(total)) {
            req.setAttribute("message", "Empty parameter exists");
            view.forward(req, resp);
            return;
        }
        int a = supermarketService.checkoutByCash(shopNumber, total);
        if (a != 0) {
            req.setAttribute("message", "checkoutByCash service problem");
            view.forward(req, resp);
            return;
        }
        Double totalCost = 0.0;
        int category = 0;
        //回显页面,先查该订单号的所有信息，
        List<OrderItemVO> ord = supermarketService.getAllChecked(Integer.parseInt(shopNumber));
        for (OrderItemVO item1 : ord) {
            totalCost += item1.getTotal();
            category += item1.getCount();
        }
        req.setAttribute("shoppingNum", shopNumber);
        req.setAttribute("orderItemList", ord);
        req.setAttribute("total_cost", String.valueOf(totalCost));
        req.setAttribute("category", String.valueOf(category));
        req.setAttribute("checkout_type", 0);
        req.setAttribute("cash_receive", cashReceive);
        req.setAttribute("cash_balance", cashBalance);
        String forwardPage = receiptPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);
    }

    private void checkoutByMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * shoppingNum: 1132301181
         * commodityID:
         * count:
         * category: 2
         * total_cost: 10.0
         * cash_receive: 20
         * cash_balance: 10
         * memberID:
         */
        RequestDispatcher view = req.getRequestDispatcher(forwardPage);
        String shopNumber = req.getParameter("shoppingNum");
        String total = req.getParameter("total_cost");
        String memberId = req.getParameter("memberID");

        if (StringUtils.isEmpty(shopNumber) && StringUtils.isEmpty(total) && StringUtils.isEmpty(memberId)) {
            req.setAttribute("message", "Empty parameter exists");
            view.forward(req, resp);
            return;
        }
        Map<String, Object> map = supermarketService.checkoutByMember(shopNumber, total, memberId);
        if (map.get("error").equals(99999999)) {
            req.setAttribute("message", "shopNumber OR  memberId is 0 ");
            view.forward(req, resp);
            return;
        }
        Double totalCost = 0.0;
        int category = 0;
        //回显页面,先查该订单号的所有信息，
        List<OrderItemVO> ord = supermarketService.getAllChecked(Integer.parseInt(shopNumber));
        for (OrderItemVO item1 : ord) {
            totalCost += item1.getTotal();
            category += item1.getCount();
        }
        int totalMember = new Double(totalCost).intValue();
        req.setAttribute("shoppingNum", shopNumber);
        req.setAttribute("orderItemList", ord);
        req.setAttribute("total_cost", String.valueOf(totalCost));
        req.setAttribute("category", String.valueOf(category));
        req.setAttribute("checkout_type", 1);
        req.setAttribute("cash_balance", map.get("total"));
        req.setAttribute("member_id", memberId);
        req.setAttribute("member_current_points", totalMember);
        req.setAttribute("member_points", map.get("points"));
        String forwardPage = receiptPage;
        RequestDispatcher view1 = req.getRequestDispatcher(forwardPage);
        view1.forward(req, resp);
    }
}
