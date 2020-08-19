package org.acme.restclient;


import com.google.gson.Gson;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;


@Path("/qualification-check")

public class LoanCalculatorResource {

    @Inject
    @RestClient
    LoanCalculatorService onboardingService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultObj postCase(String json) {

        try {
            LinkedHashMap mapVal = new Gson().fromJson(json,LinkedHashMap.class);



            String dmnReq = "{ \"model-namespace\" : \"https://kiegroup.org/dmn/_C57E89DD-6F36-4590-809A-0B8E742F2676\", " +
                    "\"model-name\" : \"ProcessFailureDMN\", \"dmn-context\" : {\"SensuEvents\" : "+new Gson().toJson(mapVal.get("sensuEvent"))+", " +
                    "\"ApbRuns\": " +
                    new Gson().toJson(mapVal.get("playbookEvents"))+"," +
                    "\"Frequency\":"+mapVal.get("frequency")+"," +
                    "\"Interval\":"+mapVal.get("interval")+"}}";
            System.out.println(dmnReq);
            String response = onboardingService.checkQual(dmnReq);
//
         ResultObj resultObj = parseResults(response);

            System.out.println(mapVal);

            return resultObj;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResultObj parseResults(String response) {
        LinkedHashMap resVal = new Gson().fromJson(response,LinkedHashMap.class);

        LinkedHashMap result = new Gson().fromJson(new Gson().toJson(resVal.get("result")),LinkedHashMap.class);

        LinkedHashMap dmnEvalResult = new Gson().fromJson(new Gson().toJson(result.get("dmn-evaluation-result")),LinkedHashMap.class);

        LinkedHashMap decisionresult = new Gson().fromJson(new Gson().toJson(dmnEvalResult.get("decision-results")),LinkedHashMap.class);

        LinkedHashMap finaldecision = new Gson().fromJson(new Gson().toJson(decisionresult.get("_55210F26-19E5-4B5B-BA17-1EE73BC4AF69")),LinkedHashMap.class);


        System.out.println(finaldecision.get("result").toString());

        LinkedHashMap final1 = new Gson().fromJson(new Gson().toJson(decisionresult.get("_8F231DF5-F27F-4357-8536-85EDC5FF41D5")),LinkedHashMap.class);



        ResultObj resultObj = new ResultObj();
        resultObj.setApbName(finaldecision.get("result").toString());


        if(null != final1 && final1.get("result") != null) {
            System.out.println(final1.get("result").toString());
            resultObj.setCanInvoke(final1.get("result").toString());

        }

        return resultObj;
    }

}