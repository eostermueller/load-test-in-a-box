package com.github.eostermueller.snail4j.util;

import java.io.IOException;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

public class JsonPatchUtils {

	public JsonNode patch(JsonNode sourceDoc, JsonNode patchDoc) throws  IOException, JsonPatchException {
		final JsonPatch patch = JsonPatch.fromJson(patchDoc);
		JsonNode patched = null;
		patched = patch.apply(sourceDoc);
		return patched;
	}

	public JsonNode setGlowrootBindAddress(JsonNode glowrootAdminJsonFile, String bindAddress) throws IOException  {
		
		JsonNode result = null;
		
			result = upsertValue(
					glowrootAdminJsonFile, 
					"/web/bindAddress", 
					"\"" + bindAddress + "\"");
		
		return result;
	}
	public JsonNode upsertValue(JsonNode srcNode, String path, String value) throws IOException {
		
		String patch = "[\r\n" + 
				"  { \"op\": \"replace\", \"path\": \"" + path + "\", \"value\":" + value + " }\r\n]";
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode patchNode = null;
		patchNode = objectMapper.readTree(patch);		

		JsonPatchUtils utils = new JsonPatchUtils();
		
		JsonNode upsertResult = null;
		try {
			upsertResult = utils.patch(srcNode,patchNode);
		} catch (JsonPatchException e) {
			String addPatch = "[\r\n" + 
					"  { \"op\": \"add\", \"path\": \"" + path + "\", \"value\": " + value + " }\r\n" + 
					"]";
			JsonNode addPatchNode = objectMapper.readTree(addPatch);		

			try {
				upsertResult = utils.patch(srcNode,addPatchNode);
			} catch (IOException | JsonPatchException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return upsertResult;
	}
}
