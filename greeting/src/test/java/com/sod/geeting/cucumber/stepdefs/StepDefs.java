package com.sod.geeting.cucumber.stepdefs;

import com.sod.geeting.GreetingApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GreetingApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
