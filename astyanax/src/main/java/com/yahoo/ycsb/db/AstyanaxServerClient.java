package com.yahoo.ycsb.db;
import com.yahoo.ycsb.*;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class AstyanaxServerClient extends DB {
	public static final int Ok = 0;
	public static final int Error = -1;

	public static final String SERVER_IP = "server_ip";
	public static final String SERVER_IP_DEFAULT = "127.0.0.1";

	public static final String SERVER_PORT = "server_port";
	public static final String SERVER_PORT_DEFAULT = "2345";

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public void init() throws DBException {
		String serverIP = getProperties().getProperty(SERVER_IP, SERVER_IP_DEFAULT);
    	int portNumber = Integer.parseInt(getProperties().getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));

		try {
            socket = new Socket(serverIP, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
         
            System.out.println("Connecting to server...");
            System.out.println("Server response: " + in.readLine());
            
        } catch (UnknownHostException e) {
            System.err.println("Could not find server " + serverIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                serverIP);
            System.exit(1);
        }

	}
	/**
	 * Insert a record in the database. Any field/value pairs in the specified
	 * values HashMap will be written into the record with the specified record
	 * key.
	 * 
	 * @param table
	 *            The name of the table
	 * @param key
	 *            The record key of the record to insert.
	 * @param values
	 *            A HashMap of field/value pairs to insert in the record
	 * @return Zero on success, a non-zero error code on error
	 */
	public int insert(String table, String key,
		HashMap<String, ByteIterator> values) {
		try {
			String valuesToString = "";
			for (Entry<String, ByteIterator> entry : values.entrySet()) {
				valuesToString = valuesToString + entry.getKey() + "§" + entry.getValue().toString() + "|" ;
			}
			String insertRequest = "write|" + key + "|" + valuesToString;
			out.println(insertRequest);
			String serverResponse = in.readLine();
			if(serverResponse == null)
				return Error;
			if(serverResponse.equals("write:success"))
				return Ok;
			else
			return Error;
		} catch (IOException e) {
			System.out.println(e);
            return Error;
        }
		
	}

	/**
	 * Update a record in the database. Any field/value pairs in the specified
	 * values HashMap will be written into the record with the specified record
	 * key, overwriting any existing values with the same field name.
	 * 
	 * @param table
	 *            The name of the table
	 * @param key
	 *            The record key of the record to write.
	 * @param values
	 *            A HashMap of field/value pairs to update in the record
	 * @return Zero on success, a non-zero error code on error
	 */
	public int update(String table, String key,
			HashMap<String, ByteIterator> values) {
		return insert(table, key, values);
	}

	/**
	 * Read a record from the database. Each field/value pair from the result
	 * will be stored in a HashMap.
	 * 
	 * @param table
	 *            The name of the table
	 * @param key
	 *            The record key of the record to read.
	 * @param fields
	 *            The list of fields to read, or null for all of them
	 * @param result
	 *            A HashMap of field/value pairs for the result
	 * @return Zero on success, a non-zero error code on error
	 */
	public int read(String table, String key, Set<String> fields,
			HashMap<String, ByteIterator> result) {
		return Ok;
	}

	/**
	 * Delete a record from the database.
	 * 
	 * @param table
	 *            The name of the table
	 * @param key
	 *            The record key of the record to delete.
	 * @return Zero on success, a non-zero error code on error
	 */
	public int delete(String table, String key) {
		return Ok;

	}

	/**
	 * Perform a range scan for a set of records in the database. Each
	 * field/value pair from the result will be stored in a HashMap.
	 * 
	 * @param table
	 *            The name of the table
	 * @param startkey
	 *            The record key of the first record to read.
	 * @param recordcount
	 *            The number of records to read
	 * @param fields
	 *            The list of fields to read, or null for all of them
	 * @param result
	 *            A Vector of HashMaps, where each HashMap is a set field/value
	 *            pairs for one record
	 * @return Zero on success, a non-zero error code on error. See this class's
	 *         description for a discussion of error codes.
	 */
	public int scan(String table, String startkey, int recordcount,
			Set<String> fields, Vector<HashMap<String, ByteIterator>> result) {
		throw new UnsupportedOperationException(
				"Scan method is not implemented.");

	}

}