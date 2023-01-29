

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class verifyOTPServlet
 */
@WebServlet("/verifyOTPServlet")
public class verifyOTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public verifyOTPServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String id = request.getParameter("id");
		String payment = (String) request.getParameter("payment");
		String sotp = request.getParameter("otp");
		int otp = Integer.parseInt(sotp);
		
		//If result from EJB that entered OTP is valid
		if(validOTP(id,otp)) {
			updateBalance(id,payment);// deduct money from the balance of acc
			request.getRequestDispatcher("paymentStatus.jsp").forward(request, response);
		}else {// else show error message
			request.setAttribute("errorMessage", "Error: wrong OTP!");
			request.getRequestDispatcher("otp.jsp").forward(request, response);
			
		}
	}

	//method to call web service that handle EJB
	//EJB from Bank site will verify the OTP for payment sucessfully.
	public boolean validOTP(String id, int otp) {
		boolean isValidOTP = false;
		try {
		// Create a URL object for the web service endpoint
        URL url = new URL("http://localhost:8080/Bank_A/verifyOTP?id="+id+"&otp="+otp);

        // Open a connection to the web service
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        con.setRequestMethod("POST");

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
       
        String c = content.toString();
        isValidOTP = Boolean.parseBoolean(c);
        
        return isValidOTP;
        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isValidOTP;
        
	}
	
	//method to call web service that handle EJB
	//in which EJB will update the balance if payment is made successfully.
	public void updateBalance(String id,String payment) {
		try {
			// Create a URL object for the web service endpoint
	        URL url = new URL("http://localhost:8080/Bank_A/updateBalance?id="+id+"&payment="+payment);

	        // Open a connection to the web service
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        // Set the request method to POST
	        con.setRequestMethod("POST");

	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}
