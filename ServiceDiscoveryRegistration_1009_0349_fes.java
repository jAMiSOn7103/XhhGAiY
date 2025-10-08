// 代码生成时间: 2025-10-09 03:49:20
 * It uses MyBatis to interact with a database where service instances are registered.
 */

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;
import java.util.Map;

public class ServiceDiscoveryRegistration {

    private final SqlSessionFactory sqlSessionFactory;

    /**
     * Constructor that initializes the SqlSessionFactory.
     * @param sqlSessionFactory The factory to create SqlSession instances.
     */
    public ServiceDiscoveryRegistration(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Registers a new service instance.
     * @param serviceInstanceInfo The information about the service instance to register.
     * @return The registered service instance ID.
     */
    public int registerServiceInstance(Map<String, String> serviceInstanceInfo) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IServiceRegistrationMapper mapper = session.getMapper(IServiceRegistrationMapper.class);
            int serviceInstanceId = mapper.insertServiceInstance(serviceInstanceInfo);
            session.commit();
            return serviceInstanceId;
        } catch (Exception e) {
            // Handle exception appropriately, possibly rethrowing as a custom exception
            throw new RuntimeException("Error registering service instance", e);
        }
    }

    /**
     * Discovers all service instances of a particular service type.
     * @param serviceType The type of service to discover.
     * @return A list of all discovered service instances.
     */
    public List<Map<String, String>> discoverServiceInstances(String serviceType) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            IServiceRegistrationMapper mapper = session.getMapper(IServiceRegistrationMapper.class);
            List<Map<String, String>> serviceInstances = mapper.findServiceInstancesByType(serviceType);
            return serviceInstances;
        } catch (Exception e) {
            // Handle exception appropriately, possibly rethrowing as a custom exception
            throw new RuntimeException("Error discovering service instances", e);
        }
    }

    // Define the MyBatis mapper interface
    public interface IServiceRegistrationMapper {
        int insertServiceInstance(Map<String, String> serviceInstanceInfo);
        List<Map<String, String>> findServiceInstancesByType(String serviceType);
    }
}
