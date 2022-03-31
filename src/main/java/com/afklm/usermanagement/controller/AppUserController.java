package com.afklm.usermanagement.controller;

import com.afklm.usermanagement.model.AppUser;
import com.afklm.usermanagement.model.Response;
import com.afklm.usermanagement.service.AppUserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class AppUserController {

    private final AppUserServiceImpl userService;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(description = "User created successfully",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUser.class))),
            @ApiResponse(responseCode = "500",
                    description = "user can't be created, check if user is adult and resident in France")
    })
    @PostMapping("/save")
    public ResponseEntity<Response> createUser(@RequestBody @Valid AppUser user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userService.create(user)))
                        .message("User created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }


    @Operation(summary = "Search a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUser.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/get/{userId}")
    public ResponseEntity<Response> getUser(@Parameter(description = "userId to be searched")
                                            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userService.get(userId)))
                        .message("User retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
