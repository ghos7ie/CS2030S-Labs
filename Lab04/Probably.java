/**
 * This class implements something that
 * probably is just a value but may be nothing.
 * We will never return null in this class, but
 * we may return something that contains nothing
 * where the nothing is a null.
 *
 * @author Lewis Lye [14A]
 * @version CS2030S AY22/23 Semester 1
 */
class Probably<T> implements Actionable<T>, Immutatorable<T>, Applicable<T> {
  private final T value;

  private static final Probably<?> NONE = new Probably<>(null);

  /**
   * Private constructor, can only be invoked inside.
   * This is called a factory method. We can only
   * create this using the two public static method.
   *
   * @param value value of type T.
   * @return The shared NOTHING.
   */
  private Probably(T value) {
    this.value = value;
  }

  /**
   * It is probably nothing, no value inside.
   * 
   * @param <T> Explicit type parameter.
   * @returnThe shared NOTHING.
   */
  public static <T> Probably<T> none() {
    @SuppressWarnings("unchecked")
    Probably<T> res = (Probably<T>) NONE;
    return res;
  }

  /**
   * It is probably just the given value.
   * Unless the value is null, then nothing is
   * given again.
   *
   * @param value Probably this is the value
   *              unless it is null then we say
   *              that there is no
   * @return The given value or nothing but
   *         never null.
   */
  /**
   * It is probably just the given value.
   * Unless the value is null, then nothing is
   * given again.
   *
   * @param <T>   Explicit type parameter.
   * @param value Probably this is the value
   *              unless it is null then we say
   *              that there is no
   * @return The given value or nothing but
   *         never null.
   */
  public static <T> Probably<T> just(T value) {
    if (value == null) {
      return none();
    }
    return (Probably<T>) new Probably<>(value);
  }

  /**
   * Check for equality between something that
   * is probably a value but maybe nothing.
   *
   * @param obj The other value to be compared.
   * @return True if the two values are equal,
   *         false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Probably<?>) {
      Probably<?> some = (Probably<?>) obj;
      if (this.value == some.value) {
        return true;
      }
      if (this.value == null || some.value == null) {
        return false;
      }
      return this.value.equals(some.value);
    }
    return false;
  }

  /**
   * String representation of something that
   * is probably a value but maybe nothing.
   *
   * @return The string representation.
   */
  @Override
  public String toString() {
    if (this.value == null) {
      return "<>";
    } else {
      return "<" + this.value.toString() + ">";
    }
  }

  /**
   * Implementation of Actionable\<T\>. Carries out
   * the action provided.
   * 
   * @params action Action to be carried out.
   */
  @Override
  public void act(Action<? super T> action) {
    if (this.value != null) {
      action.call(this.value);
    }
  }

  /**
   * Transforms T item to Probably\<R\> item.
   * 
   * @param <R>       Explicit type parameter. Telling compiler that the type of
   *                  return will be R.
   * @param immutator Item of type T that will be changed to Immutatorable<R>.
   * 
   * @return Item of type Probably\<R\>, which is a subtype of Immutorable<R>.
   */
  @Override
  public <R> Immutatorable<R> transform(Immutator<? extends R, ? super T> immutator) {
    if (this.value != null) {
      return new Probably<R>(immutator.invoke(this.value));
    }
    return none();
  }

  /**
   * Checks if value of Probably<Integer> is divisible by IsModEq val.
   * 
   * @param val of Type IsModEq with the values to be checked against.
   * @return this if IsModEq returns true.
   *         else returns none().
   */
  @Override
  public Probably<T> check(IsModEq val) {
    if (this.value != null && this.value instanceof Integer) {
      Boolean check = val.invoke((Integer) this.value);
      if (check) {
        return this;
      } else {
        return none();
      }
    }
    return none();
  }

  /**
   * Takes in Probably<Immutator> and mutates value inside
   * Probably<Immutator>. idk why this works.
   * 
   * @param <R>      Explicit type parameter. Telling compiler that the type of
   *                 return will be R.
   * @param probably Probably<Immutator> to be mutated.
   * @return Probably<R> if immutator is this.value is not null and is an
   *         immutator.
   *         Else none();
   */
  @Override
  public <R> Probably<R> apply(Probably<Immutator<R, T>> probably) {
    if (this.value != null && probably.value != null) {
      return new Probably<R>((probably.value.invoke(this.value)));
    } else {
      return none();
    }
  }
}