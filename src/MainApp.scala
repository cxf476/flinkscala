import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import com.thomsonreuters.ap.metrics._

object MainApp {
  def main(args: Array[String]) {
    org.apache.log4j.BasicConfigurator.configure();
    // set up the execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment
 
    // get input data
    val text = env.fromElements("To be, or not to be,--that is the question:--",
      "Whether 'tis nobler in the mind to suffer", 
      "The slings and arrows of outrageous fortune",
      "Or to take arms against a sea of troubles,")
 
    val counts = text.flatMap { _.toLowerCase.split("\\W+") }
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)
 
    // emit result
    counts.print()
    MetricsService.getMetrics()
    System.out.println(SocketMetrics.DEFAULT_HOST)
  }
}