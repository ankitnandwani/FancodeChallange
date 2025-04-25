package com.cucumber.StepDefs;

import com.backend.Endpoints;
import com.backend.RequestUtils;
import com.backend.Utils;
import com.google.gson.Gson;
import entities.Todo;
import entities.users.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Log4j2
public class TodosTests {

    List<Integer> usersWithTodos;
    List<Integer> usersWithTodosFromFancode;
    Todo[] todosResponses;
    Gson gson;
    RequestUtils requestUtils;
    Utils utils;

    public TodosTests(){
        gson = new Gson();
        requestUtils = new RequestUtils();
        utils = new Utils();
    }

    @Given("^User has the todo tasks$")
    public void userHasTheTodoTasks(){
        Response response = requestUtils.sendGet(utils.getApiPath(Endpoints.TODOS), utils.getRequestHeaders());
        todosResponses = gson.fromJson(response.getBody().asString(), Todo[].class);
        usersWithTodos = Arrays.stream(todosResponses).map(Todo::getUserId).distinct().collect(Collectors.toList());
    }

    @When("^User belongs to the city FanCode$")
    public void userBelongsToTheCityFanCode(){
        usersWithTodosFromFancode = usersWithTodos.stream().filter(this::isUserFromFancode).collect(Collectors.toList());
    }

    @Then("^User Completed task percentage should be greater than (.*)$")
    public void userCompletedTaskPercentageShouldBeGreaterThan(String percent){
        for (Integer userId : usersWithTodosFromFancode) {
            Predicate<Todo> userMatch = todos -> todos.getUserId()==userId;
            long totalTasks = Arrays.stream(todosResponses).filter(userMatch).count();
            long completedTasks = Arrays.stream(todosResponses).filter(userMatch.and(Todo::isCompleted)).count();
            double percentage = ((double) completedTasks / totalTasks) * 100;
            Logger.getAnonymousLogger().info("User : " + userId + " has " + totalTasks + " total tasks and " + completedTasks + " completed tasks which is " + percentage + "%");

            int expectedPercent = Integer.parseInt(percent.substring(0,percent.length()-1));
            Assert.assertTrue(expectedPercent > 50);
        }
    }

    private boolean isUserFromFancode(int id){
        Response response = requestUtils.sendGetWithPathParams(utils.getApiPath(Endpoints.USER), utils.getRequestHeaders(), utils.getPathParam(id));
        User userResponse = gson.fromJson(response.getBody().asString(), User.class);
        double lat = Double.parseDouble(userResponse.getAddress().getGeo().getLat());
        double lng = Double.parseDouble(userResponse.getAddress().getGeo().getLng());
        return lat>=-40 && lat<=5 && lng>=5 && lng<=100;
    }
}