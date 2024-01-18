package com.example.team_project.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team_project._core.utils.ApiUtils;
import com.example.team_project._core.utils.JwtTokenUtils;
import com.example.team_project.camp._dto.CampRespDTO;
import com.example.team_project.order._dto.OrderReqDTO;
import com.example.team_project.order._dto.OrderRespDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/order")
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;
    
    // 아이디로 다가오는 캠핑 일정 조회
    @GetMapping("/imminentOrder")
    public ResponseEntity<?> imminentOrderDetail(@RequestHeader("Authorization") String token){
    	DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
    	Integer userId = decodedJWT.getClaim("id").asInt();
    	// 테스트 용 하드 코딩
//    	OrderRespDTO.ImminentOrderDetailDTO responseDTO = orderService.imminentOrderDetail(1);
    	OrderRespDTO.ImminentOrderDetailDTO responseDTO = orderService.imminentOrderDetail(userId);
    	return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
    
    // 아이디로 캠핑 일정 목록 조회
    @GetMapping("/campSchedule")
    public ResponseEntity<?> campScheduleList(@RequestHeader("Authorization") String token){
    	DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
    	Integer userId = decodedJWT.getClaim("id").asInt();
    	// 테스트 용 하드 코딩
    	//OrderRespDTO.CampScheduleListDTO responseDTO  = orderService.campScheduleList(1);
    	OrderRespDTO.CampScheduleListDTO responseDTO  = orderService.campScheduleList(userId);
    	return ResponseEntity.ok().body(ApiUtils.success(responseDTO));
    }
    
    //캠프장 아이디를 받아 캠프 구역 목록 조회 + 캠프장 지도 + 상세정보 조회
    @GetMapping("/field-list")
    public ResponseEntity<?> campFieldList(@ModelAttribute OrderReqDTO.CampFieldListDTO requestDTO){
    	CampRespDTO.CampFieldListDTO responseDTO = orderService.campFieldList(requestDTO);
    	return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    
    
    // 캠핑 결제
    @PostMapping("/payment")
    public ResponseEntity<?> paymentWrite(@RequestBody @Valid OrderReqDTO.OrderWriteDTO requestDTO , @RequestHeader("Authorization") String token){
        System.out.println("컨트롤러 진입!");
        System.out.println(requestDTO.getCampId());
        System.out.println(requestDTO.getCheckIn());
        System.out.println(requestDTO.getCheckOut());
        System.out.println(requestDTO.getFieldName());
    	DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
//    	Integer userId = decodedJWT.getClaim("id").asInt();
    	//테스트 용 하드 코딩
    	OrderRespDTO.PaymentWriteDTO responseDTO = orderService.paymentWrite(1, requestDTO);
//    	OrderRespDTO.PaymentWriteDTO responseDTO = orderService.paymentWrite(userId, requestDTO);
    	return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
    
    // 캠핑 환불 DB 처리
    @DeleteMapping("/refund")
    public ResponseEntity<?> orderDelete(@Valid OrderReqDTO.OrderDeleteDTO requestDTO 
    		,@RequestHeader("Authorization") String token){
    	DecodedJWT decodedJWT = JwtTokenUtils.verify(token);
    	Integer userId = decodedJWT.getClaim("id").asInt();
    	
    	//테스트 용 하드 코딩
//    	orderService.orderDelete(1, requestDTO);
    	orderService.orderDelete(userId, requestDTO);
    	return ResponseEntity.ok(ApiUtils.success("환불 처리 완료"));
    }
    

    
    
    
}
