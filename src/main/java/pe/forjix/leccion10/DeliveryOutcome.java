package pe.forjix.leccion10;

public sealed interface DeliveryOutcome permits Delivered, FailedAttempt, Returned, Lost {
}
