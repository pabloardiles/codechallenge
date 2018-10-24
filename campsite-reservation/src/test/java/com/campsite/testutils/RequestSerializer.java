package com.campsite.testutils;

import com.campsite.model.ReservationRequest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class RequestSerializer extends StdSerializer<ReservationRequest> {

    public RequestSerializer(Class<ReservationRequest> t) {
        super(t);
    }

    @Override
    public void serialize(
            ReservationRequest value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("id", value.getId());
        jgen.writeStringField("firstName", value.getFirstName());
        jgen.writeStringField("lastName", value.getLastName());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeStringField("arrivalDate", value.getArrivalDate().toString());
        jgen.writeStringField("departureDate", value.getDepartureDate().toString());
        jgen.writeEndObject();
    }
}
