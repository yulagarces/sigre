package service.utils.security.login;

import java.io.Serializable;
import java.security.Principal;

/**
 *
 * @author danny
 */
public class DataBasePrincipal implements Principal, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * @serial
     */
    private String name;
    /**
     * Create a SamplePrincipal with a Sample username.
     *
     * <p>
     *
     * @param name the Sample username for this user.
     *
     * @exception NullPointerException if the <code>name</code> is
     * <code>null</code>.
     */
    public DataBasePrincipal(String name) {
        if (name == null) {
            throw new NullPointerException("illegal null input");
        }
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
    /**
     * Return a string representation of this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a string representation of this <code>SamplePrincipal</code>.
     */
    public String toString() {
        return ("SamplePrincipal:  " + name);
    }
    /**
     * Compares the specified Object with this <code>SamplePrincipal</code> for
     * equality. Returns true if the given object is also a
     * <code>SamplePrincipal</code> and the two SamplePrincipals have the same
     * username.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     * <code>SamplePrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     * <code>SamplePrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof DataBasePrincipal)) {
            return false;
        }
        DataBasePrincipal that = (DataBasePrincipal) o;

        if (this.getName().equals(that.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Return a hash code for this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>SamplePrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }

}
