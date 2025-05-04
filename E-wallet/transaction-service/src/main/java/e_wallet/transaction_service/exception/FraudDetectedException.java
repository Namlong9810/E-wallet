package e_wallet.transaction_service.exception;

public class FraudDetectedException extends RuntimeException{
    public FraudDetectedException(String message) {
        super(message);
    }
}
