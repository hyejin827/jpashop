package jpabook.jpashop.controller;

import com.querydsl.core.Tuple;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.repository.OrderSearch;
import jpabook.jpashop.dto.request.OrderRequestDto;
import jpabook.jpashop.dto.response.OrderResponseDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public List<OrderResponseDto> list(@RequestParam(value = "memberName", required = false) String memberName,
                                       @RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus) {
        OrderSearch orderSearch = OrderSearch.builder()
                                    .memberName(memberName)
                                    .orderStatus(orderStatus)
                                    .build();
        return orderService.findOrders(orderSearch);
    }

    @PostMapping("/new")
    public String create(@RequestBody OrderRequestDto orderRequestDto) {
        Long id = orderService.order(orderRequestDto.getMemberId(), orderRequestDto.getItemId(), orderRequestDto.getOrderCnt());
        return "success";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancel(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "success";
    }
}
