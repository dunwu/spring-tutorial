/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.spring.data.nosql.mongodb.repository;

import example.spring.data.nosql.mongodb.entity.Customer3;
import org.springframework.data.mongodb.repository.Meta;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to manage {@link Customer3} instances.
 * @author Christoph Strobl
 */
@Repository("advancedRepository")
public interface AdvancedRepository extends CustomerRepository3 {

    String META_COMMENT = "s2gx-2014-rocks!";

    /**
     * Derived query using {@code $comment} meta attribute for quick lookup. <br /> Have a look at the {@literal mongodb
     * shell} and execute:
     *
     * <pre>
     * <code>
     *  db['system.profile'].find({'query.$comment':'s2gx-2014-rocks!'})
     * </code>
     * </pre>
     * @param firstName
     * @return
     */
    @Meta(comment = META_COMMENT)
    List<Customer3> findByFirstName(String firstName);

}
