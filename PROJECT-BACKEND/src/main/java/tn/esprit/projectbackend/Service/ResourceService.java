package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Resource;
import tn.esprit.projectbackend.Repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource updateResource(Long id, Resource updatedResource) {
        Optional<Resource> existingResource = resourceRepository.findById(id);
        if (existingResource.isPresent()) {
            Resource resource = existingResource.get();
            resource.setResourceTitle(updatedResource.getResourceTitle());
            resource.setResourceType(updatedResource.getResourceType());
            resource.setUrl(updatedResource.getUrl());
            return resourceRepository.save(resource);
        }
        return null;
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }


}
