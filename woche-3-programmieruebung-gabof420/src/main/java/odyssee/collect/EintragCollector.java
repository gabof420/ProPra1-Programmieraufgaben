package odyssee.collect;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import odyssee.Eintrag;

public class EintragCollector implements Collector<String, EintragSammler, List<Eintrag>> {
  @Override
  public Supplier<EintragSammler> supplier() {
    // FIXME Implementieren
    return EintragSammler::new;
  }

  @Override
  public BiConsumer<EintragSammler, String> accumulator() {
    // FIXME Implementieren
    return EintragSammler::reduce;
  }

  @Override
  public BinaryOperator<EintragSammler> combiner() {
    // FIXME Implementieren
    return (a, b) -> {
      a.join(b);
      return a;
    };
  }


  // Ab hier kann der Code so bleiben
  @Override
  public Function<EintragSammler, List<Eintrag>> finisher() {
    return a -> a.eintraege;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Set.of();
  }
}
