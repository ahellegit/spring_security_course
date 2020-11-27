package io.baselogic.springsecurity.audit;

/**
 * A strategy for storing Audit context information against a thread.
 *
 * <p>
 * The preferred strategy is loaded by {@link AuditContextHolder}.
 *
 * @author Ben Alex
 */
public interface AuditContextHolderStrategy {
	// ~ Methods
	// ========================================================================================================

	/**
	 * Clears the current context.
	 */
	void clearContext();

	/**
	 * Obtains the current context.
	 *
	 * @return a context (never <code>null</code> - create a default implementation if
	 * necessary)
	 */
	AuditContext getContext();

	/**
	 * Sets the current context.
	 *
	 * @param context to the new argument (should never be <code>null</code>, although
	 * implementations must check if <code>null</code> has been passed and throw an
	 * <code>IllegalArgumentException</code> in such cases)
	 */
	void setContext(AuditContext context);

	/**
	 * Creates a new, empty context implementation, for use by
	 * <tt>AuditContextRepository</tt> implementations, when creating a new context for
	 * the first time.
	 *
	 * @return the empty context.
	 */
	AuditContext createEmptyContext();

}
